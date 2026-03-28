import { Component } from '@angular/core';
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import { DashboardCliente } from "../dashboard-cliente/dashboard-cliente";
import { MatIconModule } from '@angular/material/icon';
import { DepositoCliente } from "../deposito-cliente/deposito-cliente";
import { SaqueCliente } from "../saque-cliente/saque-cliente";
import { Extrato } from "../extrato/extrato";
import { TransferenciaCliente } from "../transferencia-cliente/transferencia-cliente";
import { ClientePerfil } from "../cliente-perfil/cliente-perfil";
import { Router } from '@angular/router';
@Component({
  selector: 'app-cliente-main-page',
  imports: [DashboardCliente, MatIconModule, DepositoCliente, SaqueCliente, Extrato, TransferenciaCliente, ClientePerfil],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage {
  constructor(private router:Router){}

  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  logOut(){
    this.router.navigate(['/login']);
  }

  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Dashboard;

  changeNavigationOptions(option: ClientNavigationOptions){

    this.navigationOption = option;
  }


}
