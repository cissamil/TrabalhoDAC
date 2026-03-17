import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ManagerNavigationOptions } from '../../../models/navigationOptions';
import { TodosClientes } from '../todos-clientes/todos-clientes';
import { ClienteEspecifico } from '../cliente-especifico/cliente-especifico';
import { Top3Clientes } from '../top-3-clientes/top-3-clientes';

@Component({
  selector: 'app-gerente-main-page',
  imports: [MatIconModule, TodosClientes, ClienteEspecifico, Top3Clientes],
  templateUrl: './gerente-main-page.html',
  styleUrl: './gerente-main-page.css',
})
export class GerenteMainPage {
  public get managerNavigationOptions(): typeof ManagerNavigationOptions {
    return ManagerNavigationOptions;
  }

  navigationOption: ManagerNavigationOptions = ManagerNavigationOptions.TodosClientes;

  changeNavigationOptions(option: ManagerNavigationOptions) {
    this.navigationOption = option;
  }
}
