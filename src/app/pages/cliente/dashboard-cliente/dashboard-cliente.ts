import { Component } from '@angular/core';
import { ClientNavigationOptions } from '../../../models/navigationOptions';
import{MatIconModule} from "@angular/material/icon";
import { InfoCard } from '../../../models/info-card';

@Component({
  selector: 'app-dashboard-cliente',
  imports: [MatIconModule],
  templateUrl: './dashboard-cliente.html',
  styleUrl: './dashboard-cliente.css',
})

export class DashboardCliente {

  infoCards:InfoCard[] = [
    {
      topTitle:"Saldo Atual",
      icon:"account_balance_wallet",
      middleContent:"R$ - 500,50",
      color:"red",
      bottomText:"Saldo Negativo"
    },

    {
      topTitle:"Limite",
      icon:"trending_up",
      middleContent:"R$ 1.000,00",
      color:"black",
      bottomText:"Limite Disponível"
    },

    {
      topTitle:"Saldo Disponível",
      icon:"trending_down",
      middleContent:"R$ 499,50",
      color:"blue",
      bottomText:"Saldo + Limite"
    },
  ]





}
