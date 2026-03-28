import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ManagerNavigationOptions } from '../../../core/models/navigationOptions';
import { GerenteDashboard } from '../gerente-dashboard/gerente-dashboard';
import { TodosClientes } from '../todos-clientes/todos-clientes';
import { ClienteEspecifico } from '../cliente-especifico/cliente-especifico';
import { Top3Clientes } from '../top-3-clientes/top-3-clientes';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gerente-main-page',
  imports: [MatIconModule, GerenteDashboard, TodosClientes, ClienteEspecifico, Top3Clientes],
  templateUrl: './gerente-main-page.html',
  styleUrl: './gerente-main-page.css',
})
export class GerenteMainPage {
  
  constructor(private router: Router){}

  public get managerNavigationOptions(): typeof ManagerNavigationOptions {
    return ManagerNavigationOptions;
  }

  logOut(){
    this.router.navigate(['/login']);
  }
  
  navigationOption: ManagerNavigationOptions = ManagerNavigationOptions.Dashboard;

  changeNavigationOptions(option: ManagerNavigationOptions) {
    this.navigationOption = option;
  }
}
