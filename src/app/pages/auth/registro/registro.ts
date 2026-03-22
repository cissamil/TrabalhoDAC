import { Component } from '@angular/core';
import {NgxMaskDirective} from "ngx-mask"
import { Router } from '@angular/router';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective, FormsModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {

  constructor(private router: Router){}

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();
  

  redirectToLoginPage(){
    this.router.navigate(['/login']);
  }
  
  salario:number = 0;

  handleSalario(e:any){
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;

  }


}
