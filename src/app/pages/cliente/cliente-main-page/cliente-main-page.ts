import { Component } from '@angular/core';
import { ClientNavigationOptions } from '../../../models/navigationOptions';
import { DashboardCliente } from "../dashboard-cliente/dashboard-cliente";
import { MatIconModule } from '@angular/material/icon';
@Component({
  selector: 'app-cliente-main-page',
  imports: [DashboardCliente, MatIconModule],
  templateUrl: './cliente-main-page.html',
  styleUrl: './cliente-main-page.css',
})
export class ClienteMainPage {

  public get clientNavigationOptions() : typeof ClientNavigationOptions{
    return ClientNavigationOptions;
  }

  navigationOption: ClientNavigationOptions = ClientNavigationOptions.Dashboard;

  changeNavigationOptions(option: ClientNavigationOptions){

    this.navigationOption = option;
  }


}
