import { Router } from '@angular/router';
import { Extrato } from "../extrato/extrato";
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Cliente } from '../../../core/models/entities';
import { SaqueCliente } from "../saque-cliente/saque-cliente";
import { ClientePerfil } from "../cliente-perfil/cliente-perfil";
import { DepositoCliente } from "../deposito-cliente/deposito-cliente";
import { DashboardCliente } from "../dashboard-cliente/dashboard-cliente";
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import { TransferenciaCliente } from "../transferencia-cliente/transferencia-cliente";
import { ClienteSessionService } from '../../../core/services/session-controller.service';

@Component({
  selector: 'app-cliente-main-page',
  imports: [DashboardCliente, MatIconModule, DepositoCliente, SaqueCliente, Extrato, TransferenciaCliente, ClientePerfil],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage{
  constructor(private router:Router, private clienteSessionService: ClienteSessionService){}
  cliente!: Cliente;


  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  logOut(){
    this.clienteSessionService.clearSession();
    this.router.navigate(['/login']);

  }

  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Extrato;

  changeNavigationOptions(option: ClientNavigationOptions){

    this.navigationOption = option;
  }


}
