import { Router } from '@angular/router';
import { Extrato } from '../extrato/extrato';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { ClienteOutdated } from '../../../core/models/entities';
import { SaqueCliente } from '../saque-cliente/saque-cliente';
import { ClientePerfil } from '../cliente-perfil/cliente-perfil';
import { DepositoCliente } from '../deposito-cliente/deposito-cliente';
import { DashboardCliente } from '../dashboard-cliente/dashboard-cliente';
import { ClientNavigationOptions } from '../../../core/models/navigationOptions';
import { TransferenciaCliente } from '../transferencia-cliente/transferencia-cliente';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';

@Component({
  //limpo
  selector: 'app-cliente-main-page',
  imports: [
    DashboardCliente,
    MatIconModule,
    DepositoCliente,
    SaqueCliente,
    Extrato,
    TransferenciaCliente,
    ClientePerfil,
    MatProgressSpinner
],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage implements OnInit {
  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private authService: AuthService,
    private compositionService: CompositionService,
    private cdr: ChangeDetectorRef,
  ) {}

  cliente!: ClienteOutdated;
  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Deposito;

  isLoading:boolean = true;

  ngOnInit(): void {

    const token = this.authService.usuarioLogado;

    if (!token) {
      this.logOut();
      return;
    }

    this.compositionService.getClienteConta(token).subscribe({
      next: (responseBody) => {
        if (responseBody) {
          this.clienteService.setClienteConta(responseBody);

          this.isLoading = false;

          this.cdr.detectChanges();
        } else {
          this.logOut();
        }
        
      },
      error: (err) => {
        console.error(
          'Erro crítico ao buscar dados do cliente no Gateway:',
          err,
        );
        this.logOut();
      },
    });
  }

  public get clientNavigationOptions(): typeof ClientNavigationOptions {
    return ClientNavigationOptions;
  }

  logOut() {
    this.clienteService.clearClienteConta();
    this.authService.clearUsuarioLogado();
    this.router.navigate(['/login']);
  }

  changeNavigationOptions(option: ClientNavigationOptions) {
    this.navigationOption = option;
  }
}
