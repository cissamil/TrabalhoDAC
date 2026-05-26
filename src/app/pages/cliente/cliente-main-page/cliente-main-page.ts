import { Router } from '@angular/router';
import { Extrato } from "../extrato/extrato";
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Cliente } from '../../../core/models/entities';
import { SaqueCliente } from "../saque-cliente/saque-cliente";
import { ClientePerfil } from "../cliente-perfil/cliente-perfil";
import { DepositoCliente } from "../deposito-cliente/deposito-cliente";
import { DashboardCliente } from "../dashboard-cliente/dashboard-cliente";
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import { TransferenciaCliente } from "../transferencia-cliente/transferencia-cliente";
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { CompositionServices } from '../../../core/services/compositon-services/composition-services';

@Component({
  selector: 'app-cliente-main-page',
  imports: [DashboardCliente, MatIconModule, DepositoCliente, SaqueCliente, Extrato, TransferenciaCliente, ClientePerfil],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage implements OnInit{
  constructor(
    private router:Router,
    private clienteSessionService: ClienteSessionService,
    private clienteService: ClienteService,
    private authService: AuthServices,
    private compositionService: CompositionServices,
    private cdr: ChangeDetectorRef){}

  cliente!: Cliente;
  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Dashboard;

  ngOnInit(): void {
    // Busca os dados do cliente logado na sessão local assim que a página abre

    const token = this.authService.usuarioLogado;

    if (!token) {
    this.logOut();
    return;
  }
    this.compositionService.getClienteConta(token).subscribe({
      next: (responseBody) => {
        if (responseBody) {
          // Salva o objeto ClienteConta inteiro no localStorage de forma síncrona
          this.clienteService.setClienteConta(responseBody);

          // Força o Angular a entender que os dados base mudaram (resolve o bug do site estático do vídeo)
          this.cdr.detectChanges();
        } else {
          this.logOut();
        }
      },
      error: (err) => {
        console.error("Erro crítico ao buscar dados do cliente no Gateway:", err);
        this.logOut(); // Se a API falhar ou der 401/CORS, desloga por segurança
      }
    });
  }

    // if (clienteLogado) {
    //   this.cliente = clienteLogado;
    // } else {
    //   //se não tiver logado ele redireciona para login
    //   this.router.navigate(['/login']);
    // }}

  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  logOut(){
    this.clienteService.clearClienteConta();
    this.clienteSessionService.clearSession();
    this.router.navigate(['/login']);
  }

  changeNavigationOptions(option: ClientNavigationOptions){
    this.navigationOption = option;
  }


}
