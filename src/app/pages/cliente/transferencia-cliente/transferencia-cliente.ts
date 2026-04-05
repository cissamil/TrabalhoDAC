import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente, Conta, Movimentacao } from '../../../core/models/entities';
import { Router } from '@angular/router';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { DecimalPipe } from '@angular/common';


interface valorInfo{
  title:string;
  value:number;
  reference:string;
}

@Component({
  selector: 'app-transferencia-cliente',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './transferencia-cliente.html',
  styleUrl: './transferencia-cliente.css',
})
export class TransferenciaCliente implements OnInit{

  constructor(
    private router: Router,
    private contaService: ContaService,
    private clienteSessionService: ClienteSessionService,
  ) {}

  numeroContaDestino: string = "";
  valorTransferencia: string = '0,00';
  mensagem = "";
  corMensagem = "";
  saldo = 0;
  limite = 0;
  tipoErro = '';

  private currencyFormatter = new CurrencyFormatter();

  cliente!: Cliente;
  contaCliente!: Conta;

  ngOnInit(): void {
    const dadosCliente = this.clienteSessionService.getCliente();
    const dadosConta = this.clienteSessionService.getConta();

    if (dadosCliente && dadosConta) {
      this.cliente = dadosCliente;
      this.contaCliente = dadosConta;

      this.inicializarTransferencia();
    } else {
      this.router.navigate(['/login']);
    }
  }

    get saldoDisponivelTotal():number{
    return this.saldo + this.limite;
  }

  get valorASacar():number{
    
    return this.currencyFormatter.removeCurrencyMaskFromString(this.valorTransferencia);
  }

  get novoSaldo(): number{
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(this.valorTransferencia);

    return this.saldo - valor;
  }

  get listaValoresInfo(): valorInfo[]{
    return [
    {
      title: 'Saldo Atual',
      value: this.saldo,
      reference: 'saldoAtual'
    },
    {
      title: 'Limite Disponível',
      value: this.limite,
      reference: 'limite'
    },
    {
      title: 'Saldo Disponível Total',
      value: this.saldoDisponivelTotal,
      reference: 'saldoTotal'
    },
    {
      title: 'Valor a transferir',
      value: this.valorASacar,
      reference: 'valorS'
    },
    {
      title: 'Novo Saldo (Previsão)',
      value: this.novoSaldo,
      reference: 'novoS'
    }
  ];

  }

  inicializarTransferencia(){
    this.saldo = this.contaCliente.saldo;
    this.limite = this.contaCliente.limite;    
  }

  handleValorTransferencia(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorTransferencia = input.value;
  }


  transferir(){

    const valor = this.currencyFormatter.removeCurrencyMaskFromString(this.valorTransferencia);
    
    if(this.numeroContaDestino.toString().length < 4){
      this.mensagem = "Preencha a conta corretamente";
      this.tipoErro = 'erroCONTA';
      this.corMensagem = 'red';
      return;
    }

    if(valor <= 0){
      this.mensagem="Valor inválido"
      this.tipoErro = 'erroTR'
      this.corMensagem = 'red';
      return; 
    }

    if(valor > (this.saldo+this.limite)){
      this.mensagem="Saldo insuficiente"
      this.tipoErro = 'erroTR';
      this.corMensagem = 'red';
      return; 
    }


    const contaDestino = this.contaService.buscarPorNumeroConta(this.numeroContaDestino); 

    console.log("Conta de destino: ", contaDestino);
    if(!contaDestino){
      this.mensagem = 'Conta de desino não encontrada';
      this.tipoErro = 'erroCONTA';
      this.corMensagem = 'red';

      console.error("Conta não encontrada");
      return;
    }

    this.saldo= this.saldo - valor 
    this.valorTransferencia = "0,00";
    this.numeroContaDestino = "";

    contaDestino.saldo += valor;
    this.contaCliente.saldo = this.saldo;

    const contaOrigem = this.contaCliente;

    this.contaService.realizarTransferencia(contaOrigem, contaDestino, valor)

    this.corMensagem = 'green';
    this.mensagem = "Transferência realizada com sucesso";


  }


}
