import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { ManagerListTableData } from '../../../core/models/table-data';
import { Conta, GerenteAdmin } from '../../../core/models/entities';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';

@Component({
  selector: 'app-admin-gerenciar-gerentes',
  imports: [MatTableModule],
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

  remover(id:number):void{
    console.log('Tabela antes da remoção');
    if (confirm('Tem certeza que deseja remover este gerente? as contas serão transferidas.')) {
    this.gerenteService.removerGerente(id);
    this.gerentes = this.gerenteService.listarGerentes();
    this.contas = this.contaService.listarTodos();
    this.fillGerentesTable();
    this.calcularCards();
    console.log('Tabela atualizada após remoção');
  }

}
}
