import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Extrato } from './pages/cliente/extrato/extrato';
import { Registro } from './pages/auth/registro/registro';
import { DashboardCliente } from './pages/cliente/dashboard-cliente/dashboard-cliente';
import { ClienteMainPage } from './pages/cliente/cliente-main-page/cliente-main-page';
import { SaqueCliente } from './pages/cliente/saque-cliente/saque-cliente';
import { DepositoCliente } from './pages/cliente/deposito-cliente/deposito-cliente';
export const routes: Routes = [
    {path: '', redirectTo: "login", pathMatch: 'full'},

    {path:'extrato', component: Extrato},
    {path: 'login', component: Login},
    {path: 'cliente-main-page', component: ClienteMainPage},
    {path: 'deposito-cliente', component: DepositoCliente},
    {path: 'saque-cliente', component: SaqueCliente}



];
