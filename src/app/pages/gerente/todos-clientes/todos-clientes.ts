import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';

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
  imports: [FormsModule, RouterLink],
  templateUrl: './todos-clientes.html',
  styleUrl: './todos-clientes.css',
})
export class TodosClientes {
  termoBusca = '';

  readonly clientes: ClienteTabela[] = CLIENTES_MOCK.map((cliente) => {
    const contaCliente = CONTAS_MOCK.find((conta) => conta.cliente === cliente.nome);
    const { cidade, estado } = this.extrairCidadeEstado(cliente.endereco);

    return {
      cpf: cliente.cpf,
      nome: cliente.nome,
      cidade,
      estado,
      email: cliente.email,
      salario: cliente.salario,
      saldo: contaCliente?.saldo ?? 0,
      limite: contaCliente?.limite ?? 0,
    };
  })
    .sort((a, b) => a.nome.localeCompare(b.nome));

  get clientesFiltrados(): ClienteTabela[] {
    const termo = this.termoBusca.trim().toLowerCase();
    if (!termo) {
      return this.clientes;
    }

    return this.clientes.filter(
      (cliente) =>
        cliente.cpf.includes(termo.replace(/\D/g, '')) || cliente.nome.toLowerCase().includes(termo)
    );
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
}
