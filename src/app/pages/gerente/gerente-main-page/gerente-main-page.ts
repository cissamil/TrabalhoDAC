import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';

@Component({
  //limpo
  selector: 'app-gerente-main-page',
  imports: [MatIconModule, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './gerente-main-page.html',
  styleUrl: './gerente-main-page.css',
})
export class GerenteMainPage implements  OnInit {
  constructor(
    private gerenteService: GerenteService,
    private authService: AuthService,
    private router: Router,

  ){}

  ngOnInit(): void {

    const token = this.authService.usuarioLogado;
    if(!token){
      this.logOut();
      return;
    }
  }

  logOut(){
    this.gerenteService.clearGerente();
    this.gerenteService.logoutGerente();
    this.authService.clearUsuarioLogado();
    this.router.navigate(['/login']);
  }
}
