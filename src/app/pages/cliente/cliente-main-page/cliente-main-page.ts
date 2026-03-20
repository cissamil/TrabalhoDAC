import { Component } from '@angular/core';
import { ClientNavigationOptions } from '../../../models/navigationOptions';
import { DashboardCliente } from "../dashboard-cliente/dashboard-cliente";
import { MatIconModule } from '@angular/material/icon';
import { DepositoCliente } from "../deposito-cliente/deposito-cliente";
import { SaqueCliente } from "../saque-cliente/saque-cliente";
import { Extrato } from "../extrato/extrato";
@Component({
  selector: 'app-cliente-main-page',
  imports: [DashboardCliente, MatIconModule, DepositoCliente, SaqueCliente, Extrato],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage {

  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Dashboard;

  changeNavigationOptions(option: ClientNavigationOptions){

    this.navigationOption = option;
  }


}
