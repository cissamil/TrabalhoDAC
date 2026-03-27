import { Component } from '@angular/core';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';

interface ClienteTabela {
  cpf: string;
  nome: string;
  email: string;
  salario: number;
  saldo: number;
  limite: number;
}

@Component({
  selector: 'app-todos-clientes',
  imports: [],
  templateUrl: './todos-clientes.html',
  styleUrl: './todos-clientes.css',
})
export class TodosClientes {
  readonly clientes: ClienteTabela[] = CLIENTES_MOCK.map((cliente) => {
    const contaCliente = CONTAS_MOCK.find((conta) => conta.cliente === cliente.nome);

    return {
      cpf: cliente.cpf,
      nome: cliente.nome,
      email: cliente.email,
      salario: cliente.salario,
      saldo: contaCliente?.saldo ?? 0,
      limite: contaCliente?.limite ?? 0,
    };
  });

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }
}
