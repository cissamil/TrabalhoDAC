import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { MatTableModule } from '@angular/material/table';
import { Conta, GerenteAdmin } from '../../../core/models/entities';
import { ManagerListTableData } from '../../../core/models/table-data';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';

@Component({
  selector: 'app-admin-gerenciar-gerentes',
  imports: [MatTableModule, NgxMaskPipe, FormsModule, NgxMaskDirective],
  templateUrl: './adm-gerenciar-gerentes.html',
  styleUrl: './adm-gerenciar-gerentes.css',
})
export class AdminGerenciarGerentes implements OnInit{
  constructor(
    private gerenteService:GerenteService,
    private contaService:ContaService,){}

  calcularCards():void{
    this.totalGerentes=this.gerentes.length;
    this.totalContas=this.contas.length;
    this.mediaPorGerente=this.totalGerentes>0?this.totalContas/this.totalGerentes:0;
  }
  gerentes:GerenteAdmin[]=[];
  contas:Conta[]=[];
  MANAGERS_TABLE:ManagerListTableData[]=[];
  //array final da tabela

  totalGerentes:number=0;
  totalContas:number=0;
  mediaPorGerente:number=0;
  exibirFormularioNovoGerente = false;
  mensagemErro = '';
  mensagemSucesso = '';
  idGerenteEditando: number | null = null;

  novoGerente = {
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    senha: '',
  };

  displayedcColunas:string[]=[
    'Nome',
    'CPF',
    'E-mail',
    'Telefone',
    'Quantidade de Contas',
    'Ações'
  ];


  ngOnInit(): void {
    this.gerentes=this.gerenteService.listarGerentes();
    this.contas=this.contaService.listarTodos();
    this.fillGerentesTable();
    this.calcularCards();
  };


  toggleFormularioNovoGerente(): void {
    this.exibirFormularioNovoGerente = !this.exibirFormularioNovoGerente;
    this.mensagemErro = '';
    this.mensagemSucesso = '';
  }

  fillGerentesTable(): void {
    //imprime os dados dos gerentes na tela em formato de tabela
  const novosDados = this.gerentes.map((gerente) => {
      //preenche e espelha todos os dados de gerente com spreed na impressao da tabela

    const quantidadeContas = this.contas.filter(
      //faz o cruzamento e calcula a qtd de contas que cada gerente tem
      (conta) => conta.gerente === gerente.nome
    ).length;

    return {
      id:gerente.id,
      nome: gerente.nome,
      cpf: gerente.cpf,
      email: gerente.email,
      telefone: gerente.telefone,
      quantidadeClientes: quantidadeContas // mantém seu nome
    };
  });

  novosDados.sort((a, b) => a.nome.localeCompare(b.nome));
    //ordem alfabetica
    console.log(this.MANAGERS_TABLE);
    this.MANAGERS_TABLE = [...novosDados];
}

  private atualizarTela(): void {
    this.gerentes = this.gerenteService.listarGerentes();
    this.contas = this.contaService.listarTodos();
    this.fillGerentesTable();
    this.calcularCards();
  }

inserirNovoGerente(): void {
    this.mensagemErro = '';
    this.mensagemSucesso = '';

    const nome = this.novoGerente.nome.trim();
    const cpf = this.novoGerente.cpf.replace(/\D/g, '');
    const email = this.novoGerente.email.trim().toLowerCase();
    const telefone = this.novoGerente.telefone.replace(/\D/g, '');
    const senha = this.novoGerente.senha;

    if (!nome || !cpf || !email || !telefone || !senha) {
      this.mensagemErro = 'Preencha todos os campos, incluindo a senha.';
      return;
    }

    if (cpf.length !== 11) {
      this.mensagemErro = 'CPF invalido. Informe 11 digitos.';
      return;
    }

    const jaExisteCpf = this.gerenteService.listarTodos().some((item) => item.cpf === cpf);
    if (jaExisteCpf) {
      this.mensagemErro = 'Ja existe cadastro com este CPF.';
      return;
    }

    const gerentesAntesInsercao = this.gerenteService.listarGerentes();

    const novoGerente: GerenteAdmin = {
      id: 0,
      nome,
      cpf,
      email,
      telefone,
      senha,
      tipo: 'gerente',
    };

    this.gerenteService.inserir(novoGerente);

    const contaTransferida = this.transferirContaParaNovoGerente(novoGerente, gerentesAntesInsercao);

    this.atualizarTela();
    this.mensagemSucesso = contaTransferida
      ? `Novo gerente cadastrado com sucesso. Conta ${contaTransferida.numeroConta} transferida automaticamente.`
      : `Novo gerente cadastrado com sucesso. Nenhuma conta foi transferida.`;

    this.novoGerente = {
      nome: '',
      cpf: '',
      email: '',
      telefone: '',
      senha: '',
    };
  }
  private obterMenorSaldoPositivo(contas: Conta[]): number {
    const menoresSaldosPositivos = contas
      .map((conta) => conta.saldo)
      .filter((saldo) => saldo > 0)
      .sort((a, b) => a - b);

    return menoresSaldosPositivos[0] ?? Number.POSITIVE_INFINITY;
  }

  private selecionarContaParaTransferencia(contas: Conta[]): Conta | null {
    const contasPositivas = contas
      .filter((conta) => conta.saldo > 0)
      .sort((a, b) => a.saldo - b.saldo);

    if (contasPositivas.length > 0) {
      return contasPositivas[0];
    }

    const contasOrdenadas = contas.slice().sort((a, b) => a.saldo - b.saldo);
    return contasOrdenadas[0] ?? null;
  }

  private transferirContaParaNovoGerente(
    novoGerente: GerenteAdmin,
    gerentesAntesInsercao: GerenteAdmin[],
  ): Conta | null {
    if (gerentesAntesInsercao.length === 0) {
      return null;
    }

    const contasAtuais = this.contaService.listarTodos();
    if (contasAtuais.length === 0) {
      return null;
    }

    if (gerentesAntesInsercao.length === 1) {
      const contasGerenteUnico = contasAtuais.filter(
        (conta) => conta.gerente === gerentesAntesInsercao[0].nome
      );

      if (contasGerenteUnico.length <= 1) {
        return null;
      }
    }

    const contagemPorGerente = gerentesAntesInsercao.map((gerente) => {
      const contasGerente = contasAtuais.filter((conta) => conta.gerente === gerente.nome);
      return {
        gerente,
        contas: contasGerente,
      };
    });

    const maxContas = Math.max(...contagemPorGerente.map((item) => item.contas.length));
    if (maxContas <= 0) {
      return null;
    }

    const candidatos = contagemPorGerente.filter((item) => item.contas.length === maxContas);
    const gerenteDoador = candidatos
      .map((item) => ({
        ...item,
        menorSaldoPositivo: this.obterMenorSaldoPositivo(item.contas),
      }))
      .sort((a, b) => {
        if (a.menorSaldoPositivo !== b.menorSaldoPositivo) {
          return a.menorSaldoPositivo - b.menorSaldoPositivo;
        }

        return a.gerente.nome.localeCompare(b.gerente.nome);
      })[0];

    if (!gerenteDoador) {
      return null;
    }

    const contaEscolhida = this.selecionarContaParaTransferencia(gerenteDoador.contas);
    if (!contaEscolhida) {
      return null;
    }

    const contaAtualizada: Conta = {
      ...contaEscolhida,
      gerente: novoGerente.nome,
      cpfGerente: novoGerente.cpf,
    };

    this.contaService.atualizarConta(contaAtualizada);
    return contaAtualizada;
  }

  remover(id:number):void{
    const confirmarRemocao: boolean = confirm(
      'Tem certeza que deseja remover este gerente? As contas serão transferidas.'
    );

    if (!confirmarRemocao) return;
    const listaGerentes= this.gerenteService.listarGerentes();

    const quaseTodosGerentes= listaGerentes.filter( gerente => gerente.id !== id);

    const gerenteEmExclusao = listaGerentes.find(g => g.id === id);
    if (!gerenteEmExclusao) return;

    const contagem=quaseTodosGerentes.map(contador=>({
      dados:contador,
      qtdContas: this.contaService.contarContasGerente(contador.nome)
    }));
    contagem.sort((a,b)=>a.qtdContas-b.qtdContas);
    const sucessor=contagem[0].dados;

    this.contaService.substituirGerente(gerenteEmExclusao.nome,sucessor.nome);


    this.gerenteService.removerGerente(id);

    this.gerentes = this.gerenteService.listarGerentes();

    this.contas = this.contaService.listarTodos();

    this.fillGerentesTable();

    this.calcularCards();

    alert(`Contas de ${gerenteEmExclusao.nome} transferidas para ${sucessor.nome}`);

}

editarGerente(): void {

  if (!this.idGerenteEditando) return;

  const gerenteOriginal = this.gerenteService
    .listarTodos()
    .find(g => g.id === this.idGerenteEditando);

  if (!gerenteOriginal) return;

  const gerenteAtualizado: GerenteAdmin = {
    id: gerenteOriginal.id,
    cpf: gerenteOriginal.cpf,
    tipo: gerenteOriginal.tipo,

    nome: this.novoGerente.nome,
    email: this.novoGerente.email,
    telefone: this.novoGerente.telefone,
    senha: this.novoGerente.senha,
  };

  this.gerenteService.atualizar(gerenteAtualizado);

  this.atualizarTela();

  this.idGerenteEditando = null;

  this.novoGerente = {
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    senha: '',
  };

  this.exibirFormularioNovoGerente = false;
}

  atualizarGerente(id: number) {

  const gerente = this.gerenteService
    .listarTodos()
    .find(g => g.id === id);

  if (!gerente) return;

  this.idGerenteEditando = id;
  this.exibirFormularioNovoGerente = true;

  this.novoGerente = {
    nome: gerente.nome,
    cpf: gerente.cpf,
    email: gerente.email,
    telefone: gerente.telefone,
    senha: gerente.senha
  };
}
salvarGerente(): void {

  if (this.idGerenteEditando) {
    this.editarGerente();
  } else {
    this.inserirNovoGerente();
  }
}

}
