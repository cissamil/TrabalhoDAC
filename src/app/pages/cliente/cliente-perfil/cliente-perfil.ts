import { Component } from '@angular/core';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { FormsModule } from "@angular/forms";
import { DecimalPipe } from '@angular/common';
import { NgxMaskDirective } from "ngx-mask";
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective],
  templateUrl: './cliente-perfil.html',
  styleUrl: './cliente-perfil.css',
})
export class ClientePerfil {

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  cliente = CLIENTES_MOCK[0];
  conta_cliente = CONTAS_MOCK[0];
  endereco = this.cliente.endereco.split(' - ');

  cep = this.endereco[0];
  rua = this.endereco[1];
  cidade = this.endereco[2];
  estado = this.endereco[3];
  salario = this.currencyFormatter.applyCurrencyMaskOnNumber(this.cliente.salario);

  handleSalario(e:any){
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;

  }

}
