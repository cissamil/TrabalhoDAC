import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { GerenteService } from '../../../core/services/gerente-services';

@Component({
  selector: 'app-gerente-main-page',
  imports: [MatIconModule, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './gerente-main-page.html',
  styleUrl: './gerente-main-page.css',
})
export class GerenteMainPage {
  private readonly router = inject(Router);
  private readonly gerenteService = inject(GerenteService);

  logOut(){
    this.gerenteService.logout();
    this.router.navigate(['/login']);
  }
}
