import { Component } from '@angular/core';
import { AdminNavigationOptions } from '../../../core/models/navigationOptions';
import { MatIconModule } from '@angular/material/icon';
import { DecimalPipe } from '@angular/common';
import { AdminDashboard } from "../admin-dashboard/admin-dashboard";
import { AdminRelatorioClientes } from '../admin-relatorio-clientes/admin-relatorio-clientes';
import { AdminGerenciarGerentes } from "../adm-gerenciar-gerentes/adm-gerenciar-gerentes";
import { Route, Router } from '@angular/router';

@Component({
  imports: [MatIconModule, AdminDashboard, AdminRelatorioClientes, AdminGerenciarGerentes],
  templateUrl: './admin-main-page.html',
  styleUrl: './admin-main-page.css',
})
export class AdminMainPage {

  constructor(private router:Router){}

  logOut(){
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
