import { DecimalPipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from "@angular/material/icon";
import { ClienteConta } from '../../../core/models/ClienteConta';
import { InfoCard } from '../../../core/models/info-card';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
@Component({
  selector: 'app-dashboard-cliente',
  imports: [MatIconModule, DecimalPipe],
  templateUrl: './dashboard-cliente.html',
  styleUrl: './dashboard-cliente.css',
})
export class DashboardCliente implements OnInit {

  constructor(
    private cdr: ChangeDetectorRef,
    private clienteService: ClienteService){}

  clienteConta!: ClienteConta;

  infoCards: InfoCard[] = [];
  nomeGerente:string =  "";
  nomeCliente:string =  "";

  ngOnInit(): void {

    const dadosCarregados = this.clienteService.clienteContaLogado;

    if (dadosCarregados) {
      this.clienteConta = dadosCarregados;

      this.inicializarDashboard();

      this.cdr.detectChanges();

    } else {
      console.log("Nenhum dado encontrado no localStorage para o Dashboard.");
    }
  }


inicializarDashboard() {
    const saldoStatus = this.clienteConta.conta.saldo >= 0;
    const saldoDisponivel = this.clienteConta.conta.limite + this.clienteConta.conta.saldo;
    const color = saldoStatus ? 'black' : 'red';
    const saldoInfo = saldoStatus ? 'Saldo Positivo' : 'Saldo Negativo';
    this.nomeGerente = this.clienteConta.gerente?.nomeGerente
    this.nomeCliente = this.clienteConta.cliente.nome;

    this.infoCards = [
      {
        topTitle: 'Saldo Atual',
        icon: 'account_balance_wallet',
        middleContent: this.clienteConta.conta.saldo.toString(),
        color: color,
        bottomText: saldoInfo,
      },
      {
        topTitle: 'Limite',
        icon: 'trending_up',
        middleContent: this.clienteConta.conta.limite.toString(),
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
