import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private router: Router){ }

  redirectToRegisterPage(){

    this.router.navigate(['/registro']);
    
  }

  redirectToClienteDashboardPage(){
    this.router.navigate(['/cliente-main-page'])
  }

}
