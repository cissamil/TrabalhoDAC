import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
//ngModel
@Component({
  selector: 'app-saque-cliente',
  imports: [FormsModule],
  templateUrl: './saque-cliente.html',
  styleUrl: './saque-cliente.css'
})

export class SaqueCliente {

  saldo = 500;
  limite = 300;
  valorSaque=0;
  mensagem='';
  //saldoCalculado = this.saldo + this.limite;

  sacar(){
    console.log('clicou');
    //user digita o valorSaque = 30 reais
    //usuario clica no btn sacar
    //sistema verifica se a entrada é valida ou se digitou errado
    if (this.valorSaque <=0)  { //isValid
      return this.mensagem="Valor inválido"
    };
    //sistema verifica se o valorSaque é menor que o saldoCalculado
    if (this.valorSaque > (this.saldo+this.limite)) {
      return this.mensagem="Saldo insuficiente"
    }
    //sistema retorna "tem certeza que vc quer sacar valorSaque?" == front
    //usuario confirma

    this.saldo=this.saldo-this.valorSaque
    this.valorSaque=0;
    //limpando o valor do input
    this.mensagem="Saque realizado com sucesso"
    return
    //sistema atualiza saldo
    //sistema mostra mensagem "saque feito com sucesso"

  }
}
