import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-extrato',
  imports: [FormsModule],
  templateUrl: './extrato.html',
  styleUrl: './extrato.css',
})
export class Extrato {

  mensagem="";
  dataInicio: any;
  dataFim:any;

  tirarExtrato() {
    this.mensagem="Extrato realizado com sucesso"
    return
  }
}
