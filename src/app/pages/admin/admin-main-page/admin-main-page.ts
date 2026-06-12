//ok
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AdminNavigationOptions } from '../../../core/models/navigationOptions';
import { MatIconModule } from '@angular/material/icon';
import { AdminDashboard } from "../admin-dashboard/admin-dashboard";
import { AdminRelatorioClientes } from '../admin-relatorio-clientes/admin-relatorio-clientes';
import { AdminGerenciarGerentes } from "../adm-gerenciar-gerentes/adm-gerenciar-gerentes";
import { Router } from '@angular/router';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { CompositionServices } from '../../../core/services/compositon-services/composition-services';

@Component({
  imports: [MatIconModule, AdminDashboard, AdminRelatorioClientes, AdminGerenciarGerentes],
  templateUrl: './admin-main-page.html',
  styleUrl: './admin-main-page.css',
})
export class AdminMainPage implements OnInit{

  constructor(
    private gerenteService: GerenteService,
    private authService: AuthServices,
    private cdr : ChangeDetectorRef,
    private router: Router,
    private compositionService: CompositionServices,
  ){}

  ngOnInit(): void{
    const token = this.authService.usuarioLogado;
        if(!token){
          this.logOut();
          return;
        }

    this.compositionService.getAdmin(token).subscribe({
      next:(responseBody)=>{
        if(responseBody){
          this.gerenteService.setAdmin(responseBody);
          this.cdr.detectChanges();
        }else {
          this.logOut();
        }
      },
      error: (err) => {
        console.error(
          'Erro crítico ao buscar dados do admin no Gateway:',
          err,
        );
        this.logOut();
      },
    })
  }

  logOut(){
    this.gerenteService.clearAdmin();
    this.gerenteService.logoutAdmin();
    this.router.navigate(['/login']);
  }

  public get adminNavigationOptions() : typeof AdminNavigationOptions{
    return AdminNavigationOptions;
  }

  navigationOption: AdminNavigationOptions = AdminNavigationOptions.Dashboard;

  changeNavigationOptions(option: AdminNavigationOptions){

    this.navigationOption = option;
  }
}
