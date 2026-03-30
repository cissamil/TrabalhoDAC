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
export const routes: Routes = [
    {path: '', redirectTo: "login", pathMatch: 'full'},

    {path: 'login', component: Login},
    {path: 'registro', component: Registro},
    {path: 'extrato', component: Extrato},
    {path: 'cliente-main-page', component: ClienteMainPage},
    {path: 'gerente-main-page', component: GerenteMainPage},
    {path: 'admin-main-page', component: AdminMainPage},
    {path: 'deposito-cliente', component: DepositoCliente},
    {path: 'saque-cliente', component: SaqueCliente},
    {path: 'transferencia-cliente', component: TransferenciaCliente},
    {path: 'admin-gerenciar-gerentes', component: AdminGerenciarGerentes},



];
