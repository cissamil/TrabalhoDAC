import { Component } from '@angular/core';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';

interface TopCliente {
  cpf: string;
  nome: string;
  cidade: string;
  estado: string;
  saldo: number;
}

@Component({
  selector: 'app-top-3-clientes',
  imports: [],
  templateUrl: './top-3-clientes.html',
  styleUrl: './top-3-clientes.css',
})
export class Top3Clientes {
  topClientes: TopCliente[] = CONTAS_MOCK
    .map((conta) => {
      const cliente = CLIENTES_MOCK.find((item) => item.cpf === conta.cpfCliente);
      if (!cliente) {
        return null;
      }

      const { cidade, estado } = this.extrairCidadeEstado(cliente.endereco);
      return {
        cpf: cliente.cpf,
        nome: cliente.nome,
        cidade,
        estado,
        saldo: conta.saldo,
      };
    })
    .filter((item): item is TopCliente => item !== null)
    .sort((a, b) => b.saldo - a.saldo)
    .slice(0, 3);

  private extrairCidadeEstado(endereco: string): { cidade: string; estado: string } {
    const partes = endereco.split(' - ').map((item) => item.trim());
    const estado = partes.at(-1) ?? '-';
    const cidade = partes.at(-2) ?? '-';

    return { cidade, estado };
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
