//ok
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AdminNavigationOptions } from '../../../core/models/navigationOptions';
import { MatIconModule } from '@angular/material/icon';
import { AdminDashboard } from "../admin-dashboard/admin-dashboard";
import { AdminRelatorioClientes } from '../admin-relatorio-clientes/admin-relatorio-clientes';
import { AdminGerenciarGerentes } from "../adm-gerenciar-gerentes/adm-gerenciar-gerentes";
import { Router } from '@angular/router';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';

@Component({
  imports: [MatIconModule, AdminDashboard, AdminRelatorioClientes, AdminGerenciarGerentes],
  templateUrl: './admin-main-page.html',
  styleUrl: './admin-main-page.css',
})
export class AdminMainPage implements OnInit{

  constructor(
    private gerenteService: GerenteService,
    private authService: AuthService,
    private cdr : ChangeDetectorRef,
    private router: Router,
    private compositionService: CompositionService,
  ){}

  ngOnInit(): void{
    const token = this.authService.usuarioLogado;

    if(!token){
      this.logOut();
      return;
    }
  }

  logOut(){
    this.gerenteService.clearAdmin();
    this.gerenteService.logoutAdmin();
    this.router.navigate(['/login']);
  }

  public get adminNavigationOptions() : typeof AdminNavigationOptions{
    return AdminNavigationOptions;
  }

  navigationOption: AdminNavigationOptions = AdminNavigationOptions.GerenciarGerentes;

  changeNavigationOptions(option: AdminNavigationOptions){

    this.navigationOption = option;
  }
}
