import { Component } from '@angular/core';
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import{MatIconModule} from "@angular/material/icon";
import { InfoCard } from '../../../core/models/info-card';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-dashboard-cliente',
  imports: [MatIconModule, DecimalPipe],
  templateUrl: './dashboard-cliente.html',
  styleUrl: './dashboard-cliente.css',
})
export class DashboardCliente {
  cliente = CLIENTES_MOCK[0];
  conta_cliente = CONTAS_MOCK[0];

  saldoStatus = this.conta_cliente.saldo > 0;

  saldoDisponivel = this.conta_cliente.limite + this.conta_cliente.saldo;
  color = this.saldoStatus ? 'black' : 'red';
  saldoInfo = this.saldoStatus ? 'Saldo Positivo' : 'Saldo Negativo';

  infoCards: InfoCard[] = [
    {
      topTitle: 'Saldo Atual',
      icon: 'account_balance_wallet',
      middleContent: this.conta_cliente.saldo.toString(),
      color: this.color,
      bottomText: this.saldoInfo,
    },

    {
      topTitle: 'Limite',
      icon: 'trending_up',
      middleContent: this.conta_cliente.limite.toString(),
      color: 'black',
      bottomText: 'Limite Disponível',
    },

    {
      topTitle: 'Saldo Disponível',
      icon: 'trending_down',
      middleContent: `${this.saldoDisponivel}`,
      color: 'blue',
      bottomText: 'Saldo + Limite',
    },
  ];
}
