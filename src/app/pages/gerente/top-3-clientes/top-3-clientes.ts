import { Component } from '@angular/core';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';

interface TopCliente {
  posicao: number;
  cpf: string;
  nome: string;
  saldo: number;
  limite: number;
}

@Component({
  selector: 'app-top-3-clientes',
  imports: [],
  templateUrl: './top-3-clientes.html',
  styleUrl: './top-3-clientes.css',
})
export class Top3Clientes {
  topClientes: TopCliente[] = CLIENTES_MOCK.map((cliente) => {
    const conta = CONTAS_MOCK.find((item) => item.cliente === cliente.nome);

    return {
      posicao: 0,
      cpf: cliente.cpf,
      nome: cliente.nome,
      saldo: conta?.saldo ?? 0,
      limite: conta?.limite ?? 0,
    };
  })
    .sort((a, b) => b.saldo - a.saldo)
    .slice(0, 3)
    .map((cliente, index) => ({
      ...cliente,
      posicao: index + 1,
    }));

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }
}
