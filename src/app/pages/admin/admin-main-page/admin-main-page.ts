import { Component } from '@angular/core';
import { AdminNavigationOptions } from '../../../core/models/navigationOptions';
import { MatIconModule } from '@angular/material/icon';
import { DecimalPipe } from '@angular/common';
import { AdminDashboard } from "../admin-dashboard/admin-dashboard";
import { AdminRelatorioClientes } from '../admin-relatorio-clientes/admin-relatorio-clientes';

@Component({
  selector: 'app-admin-main-page',
  imports: [MatIconModule, AdminDashboard, AdminRelatorioClientes],
  templateUrl: './admin-main-page.html',
  styleUrl: './admin-main-page.css',
})
export class AdminMainPage {

  public get adminNavigationOptions() : typeof AdminNavigationOptions{
    return AdminNavigationOptions;
  }

  navigationOption: AdminNavigationOptions = AdminNavigationOptions.Dashboard;

  changeNavigationOptions(option: AdminNavigationOptions){

    this.navigationOption = option;
  }
}
