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
export class ClienteMainPage implements OnInit{
  constructor(private router:Router,
    private clienteSessionService: ClienteSessionService){}
  cliente!: Cliente;
  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Dashboard;

  ngOnInit(): void {
    // Busca os dados do cliente logado na sessão local assim que a página abre
    const clienteLogado = this.clienteSessionService.getCliente();

    if (clienteLogado) {
      this.cliente = clienteLogado;
    } else {
      //se não tiver logado ele redireciona para login
      this.router.navigate(['/login']);
    }}

  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  logOut(){
    this.clienteSessionService.clearSession();
    this.router.navigate(['/login']);
  }

  changeNavigationOptions(option: ClientNavigationOptions){
    this.navigationOption = option;
  }


}
