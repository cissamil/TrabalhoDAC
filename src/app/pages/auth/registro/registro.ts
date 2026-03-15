import { Component } from '@angular/core';
import {NgxMaskDirective} from "ngx-mask"
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {

  constructor(private router: Router){}

  redirectToLoginPage(){
    this.router.navigate(['/login']);
  }

}
