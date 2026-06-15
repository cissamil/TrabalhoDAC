import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ClienteOutdated, ContaOutdated } from '../../../core/models/entities';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { combineLatest } from 'rxjs';

interface ClienteTabela {
  cpf: string;
  nome: string;
  cidade: string;
  estado: string;
  email: string;
  salario: number;
  saldo: number;
  limite: number;
}

@Component({
  selector: 'app-todos-clientes',
  imports: [FormsModule, RouterLink, NgxMaskDirective],
  templateUrl: './todos-clientes.html',
  styleUrl: './todos-clientes.css',
})
export class TodosClientes implements OnInit{
  termoBusca = '';
  clientes: ClienteTabela[] = [];
  tokenJWT = '';

  constructor(
    private clienteService: ClienteService,
    private contaService: ContaService,
    private gerenteService: GerenteService,
    private authService: AuthService
  ) {}

  ngOnInit(): void{
    this.tokenJWT = this.authService.usuarioLogado || '';
    this.listarClientes();
  }

  listarClientes(): void {
    const gerenteLogado = this.gerenteService.GerenteLogado;

    if (!gerenteLogado) {
      console.warn('Nenhum gerente logado encontrado.');
      this.clientes = [];
      return;
    }

    // combineLatest([
    //   this.clienteService.listarTodos(this.tokenJWT),
    //   this.contaService.listarTodos(this.tokenJWT)
    // ]).subscribe({
    //   next: ([listaClientes, listaContas]: [Cliente[], Conta[]]) => {

    //     // Mapeia as contas por CPF do cliente para busca em tempo O(1)
    //     const contasPorCpf = new Map<string, Conta>(
    //       (listaContas || []).map((conta) => [conta.cpfCliente, conta])
    //     );

    //     this.clientes = (listaClientes || [])
    //       .map((cliente) => {
    //         const contaCliente = contasPorCpf.get(cliente.cpf);

    //         // Se o cliente não possuir conta associada, ignora
    //         if (!contaCliente) return null;

    //         // 🎯 REGRA DE NEGÓCIO REATIVADA: Só exibe se a conta pertencer a este gerente
    //         const pertenceAoGerente = contaCliente.cpfGerente === gerenteLogado.cpf;
    //         if (!pertenceAoGerente) return null;

    //         const { cidade, estado } = this.extrairCidadeEstado(cliente.endereco || '');

    //         return {
    //           cpf: cliente.cpf,
    //           nome: cliente.nome,
    //           cidade,
    //           estado,
    //           email: cliente.email,
    //           salario: cliente.salario,
    //           saldo: contaCliente.saldo ?? 0,
    //           limite: contaCliente.limite ?? 0
    //         };
    //       })
    //       // Limpa os registros nulos (clientes de outras agências/gerentes)
    //       .filter((item): item is ClienteTabela => item !== null)
    //       // Ordena alfabeticamente
    //       .sort((a, b) => a.nome.localeCompare(b.nome));
    //   },
    //   error: (erro) => {
    //     console.error('Erro ao listar clientes e contas no dashboard', erro);
    //     this.clientes = [];
    //   }
    // });
  }


  get clientesFiltrados(): ClienteTabela[] {
    const termo = this.termoBusca.trim().toLowerCase();
    if (!termo) {
      return this.clientes;
    }

    const termoCpf = termo.replace(/\D/g, '');

    return this.clientes.filter(
      (cliente) => {
        const cpfNumerico = cliente.cpf.replace(/\D/g, '');
        const nomeNormalizado = cliente.nome.toLowerCase();

        const correspondeCpf = termoCpf.length > 0 && cpfNumerico.startsWith(termoCpf);
        const correspondeNome = nomeNormalizado.startsWith(termo);

        return correspondeCpf || correspondeNome;
      }
    );
  }

  get isBuscaCpf(): boolean {
    return this.comecaComNumero(this.termoBusca);
  }

  onTermoBuscaChange(valor: string): void {
    if (!valor) {
      this.termoBusca = '';
      return;
    }

    if (this.comecaComNumero(valor)) {
      this.termoBusca = valor.slice(0, 14);
      return;
    }

    this.termoBusca = valor.slice(0, 50);
  }

  private extrairCidadeEstado(endereco: string): { cidade: string; estado: string } {
    if (!endereco || !endereco.includes(' - ')) {
      return { cidade: 'Não Informada', estado: '-' };
    }
    const partes = endereco.split(' - ').map((item) => item.trim());
    return {
      cidade: partes.at(-2) ?? '-',
      estado: partes.at(-1) ?? '-',
    };
  }

  formatarCpf(cpf: string): string {
    if (!cpf) return '';
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }

  private comecaComNumero(valor: string): boolean {
    const texto = valor.trimStart();
    if (!texto) {
      return false;
    }

    return /^\d/.test(texto);
  }
}
