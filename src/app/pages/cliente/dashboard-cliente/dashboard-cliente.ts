import { Component, OnInit } from '@angular/core';
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import{MatIconModule} from "@angular/material/icon";
import { InfoCard } from '../../../core/models/info-card';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { DecimalPipe } from '@angular/common';
import { ClienteSessionService } from '../../../core/services/cliente-services/cliente-session.service';
import { Cliente, Conta } from '../../../core/models/entities';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-cliente',
  imports: [MatIconModule, DecimalPipe],
  templateUrl: './dashboard-cliente.html',
  styleUrl: './dashboard-cliente.css',
})
export class DashboardCliente implements OnInit {

  constructor(private router:Router, private clienteSessionService: ClienteSessionService){}
  
  cliente!: Cliente;
  conta_cliente!: Conta;
  infoCards: InfoCard[] = []; 

  ngOnInit(): void {

    const dadosCliente = this.clienteSessionService.getCliente();
    const dadosConta = this.clienteSessionService.getConta();

    if(dadosCliente && dadosConta){
      this.cliente = dadosCliente;
      this.conta_cliente = dadosConta;

      this.inicializarDashboard();
    }else{

      this.router.navigate(['/login']);
    }
  }
  
inicializarDashboard() {
    const saldoStatus = this.conta_cliente.saldo >= 0;
    const saldoDisponivel = this.conta_cliente.limite + this.conta_cliente.saldo;
    const color = saldoStatus ? 'black' : 'red';
    const saldoInfo = saldoStatus ? 'Saldo Positivo' : 'Saldo Negativo';

    this.infoCards = [
      {
        topTitle: 'Saldo Atual',
        icon: 'account_balance_wallet',
        middleContent: this.conta_cliente.saldo.toString(),
        color: color,
        bottomText: saldoInfo,
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
        middleContent: `${saldoDisponivel}`,
        color: 'blue',
        bottomText: 'Saldo + Limite',
      }
    ];
  }
}
