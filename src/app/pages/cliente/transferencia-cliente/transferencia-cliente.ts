import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transferencia-cliente',
  imports: [FormsModule],
  templateUrl: './transferencia-cliente.html',
  styleUrl: './transferencia-cliente.css',
})
export class TransferenciaCliente {
  contaDestino = "";
  valorTransferencia=0;
  mensagem="";
  saldo=100;
  limite=0;

  transferir(){
    if(this.valorTransferencia<=0){
      return this.mensagem="Valor inválido"
    }
    if(this.valorTransferencia>(this.saldo+this.limite)){
      return this.mensagem="Saldo insuficiente"
    }
    if(this.contaDestino){
      //se não existir ou for null
    }
    this.saldo=this.saldo-this.valorTransferencia
    this.valorTransferencia=0;
    this.contaDestino="";
    this.mensagem="Transferência realizada com sucesso"
    return

  }

}
