import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-deposito-cliente',
  imports: [FormsModule],
  templateUrl: './deposito-cliente.html',
  styleUrl: './deposito-cliente.css',
})
export class DepositoCliente {
  saldo=500;
  limite=300;
  valorDeposito=0;
  mensagem="";

  depositar(){
    //user digita valor para deposito
    if (this.valorDeposito<=0){
      return this.mensagem="O valor do depósito deve ser maior que zero"
    }
    //sistema valida os caracteres
    //user aperta botao de confirmar deposito
    //sistema retorna "tem certeza que vc quer depositar valorDeposito?" == front
    //usuario confirma
    this.saldo=this.saldo+this.valorDeposito;
    //sistema atualiza saldo
    this.valorDeposito=0;
    //limpa input
    this.mensagem="Depósito realizado com sucesso"
    return
    //sistema mostra mensagem "deposito feito com sucesso"

  }
}
