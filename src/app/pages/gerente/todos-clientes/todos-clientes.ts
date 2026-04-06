import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';

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
export class TodosClientes {
  termoBusca = '';

  constructor(
    private clienteService: ClienteService,
    private contaService: ContaService,
    private gerenteService: GerenteService,
  ) {}

  get clientes(): ClienteTabela[] {
    const gerenteLogado = this.gerenteService.getGerenteLogado();
    if (!gerenteLogado) {
      return [];
    }

    const clientes = this.clienteService.listarTodos();
    const contas = this.contaService.listarTodos();

    return clientes
      .map((cliente) => {
        const contaCliente = contas.find((conta) => conta.cpfCliente === cliente.cpf);
        if (!contaCliente) {
          return null;
        }

        const pertenceAoGerenteLogado =
          contaCliente.cpfGerente === gerenteLogado.cpf ||
          contaCliente.gerente === gerenteLogado.nome;

        if (!pertenceAoGerenteLogado) {
          return null;
        }

        const { cidade, estado } = this.extrairCidadeEstado(cliente.endereco);
        return {
          cpf: cliente.cpf,
          nome: cliente.nome,
          cidade,
          estado,
          email: cliente.email,
          salario: cliente.salario,
          saldo: contaCliente.saldo,
          limite: contaCliente.limite,
        };
      })
      .filter((cliente): cliente is ClienteTabela => cliente !== null)
      .sort((a, b) => a.nome.localeCompare(b.nome));
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
    const partes = endereco.split(' - ').map((item) => item.trim());
    return {
      cidade: partes.at(-2) ?? '-',
      estado: partes.at(-1) ?? '-',
    };
  }

  formatarCpf(cpf: string): string {
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
