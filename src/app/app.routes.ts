import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Extrato } from './pages/cliente/extrato/extrato';
import { Registro } from './pages/auth/registro/registro';

export const routes: Routes = [
    {path: '', redirectTo: "login", pathMatch: 'full'},

    {path:'extrato', component: Extrato},
    {path: 'login', component: Login},
    {path: 'registro', component: Registro},

];
