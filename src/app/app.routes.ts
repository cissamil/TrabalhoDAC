import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Extrato } from './pages/cliente/extrato/extrato';
import { Registro } from './pages/auth/registro/registro';
import { DashboardCliente } from './pages/cliente/dashboard-cliente/dashboard-cliente';
import { ClienteMainPage } from './pages/cliente/cliente-main-page/cliente-main-page';
import { SaqueCliente } from './pages/cliente/saque-cliente/saque-cliente';
import { DepositoCliente } from './pages/cliente/deposito-cliente/deposito-cliente';
import { GerenteMainPage } from './pages/gerente/gerente-main-page/gerente-main-page';
import { TransferenciaCliente } from './pages/cliente/transferencia-cliente/transferencia-cliente';
import { AdminMainPage } from './pages/admin/admin-main-page/admin-main-page';
import { AdminGerenciarGerentes } from './pages/admin/adm-gerenciar-gerentes/adm-gerenciar-gerentes';
import { GerenteDashboard } from './pages/gerente/gerente-dashboard/gerente-dashboard';
import { TodosClientes } from './pages/gerente/todos-clientes/todos-clientes';
import { ClienteEspecifico } from './pages/gerente/cliente-especifico/cliente-especifico';
import { Top3Clientes } from './pages/gerente/top-3-clientes/top-3-clientes';
export const routes: Routes = [
  //------- LOGIN---------------//
    {path: '', redirectTo: "login", pathMatch: 'full'},
    
    {path: 'login', component: Login},
    {path: 'registro', component: Registro},
    //-----------ADMIN----------------//
    {path: 'admin-main-page', component: AdminMainPage, data:{role:'ADMIN'}},
    {path: 'admin-gerenciar-gerentes', component: AdminGerenciarGerentes, data:{role:'ADMIN'}},
    //------------GERENTE------------------//
    {
      path: 'gerente-main-page',
      component: GerenteMainPage,
      children: [
        { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
        { path: 'dashboard', component: GerenteDashboard },
        { path: 'todos-clientes', component: TodosClientes },
        { path: 'consultar-cliente', component: ClienteEspecifico },
        { path: 'consultar-cliente/:cpf', component: ClienteEspecifico },
        { path: 'top-3-clientes', component: Top3Clientes },
      ],
    },
    //--------------CLIENTE----------------//
    {path: 'cliente-main-page', component: ClienteMainPage},
    {path: 'extrato', component: Extrato},
    {path: 'deposito-cliente', component: DepositoCliente},
    {path: 'saque-cliente', component: SaqueCliente},
    {path: 'transferencia-cliente', component: TransferenciaCliente}



];
