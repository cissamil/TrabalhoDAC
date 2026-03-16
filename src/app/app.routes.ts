import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Extrato } from './pages/cliente/extrato/extrato';
import { Registro } from './pages/auth/registro/registro';
import { DashboardCliente } from './pages/cliente/dashboard-cliente/dashboard-cliente';
import { ClienteMainPage } from './pages/cliente/cliente-main-page/cliente-main-page';

export const routes: Routes = [
    {path: '', redirectTo: "login", pathMatch: 'full'},

    {path:'extrato', component: Extrato},
    {path: 'login', component: Login},
    {path: 'cliente-main-page', component: ClienteMainPage},


];
