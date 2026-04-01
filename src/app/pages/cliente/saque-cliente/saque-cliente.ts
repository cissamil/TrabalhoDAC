import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente, Conta, Movimentacao } from '../../../core/models/entities';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { Router } from '@angular/router';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { DecimalPipe } from '@angular/common';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';


interface valorInfo{
  title:string;
  value:number;
  reference:string;
}


//ngModel
@Component({
  selector: 'app-saque-cliente',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './saque-cliente.html',
  styleUrl: './saque-cliente.css',
})
export class SaqueCliente implements OnInit{
  constructor(
    private router: Router,
    private contaService: ContaService,
    private movimentacaoService: MovimentacaoService,
    private clienteSessionService: ClienteSessionService,
  ) {}

  saldo = 0;
  limite = 0;
  valorSaque: string = '0,00';
  mensagem = '';
  corMensagem = '';
  
  valoresInfo: valorInfo[] = [];
  private currencyFormatter:CurrencyFormatter = new CurrencyFormatter();

  //saldoCalculado = this.saldo + this.limite;

  cliente!: Cliente;
  contaCliente!: Conta;

  ngOnInit(): void {
    const dadosCliente = this.clienteSessionService.getCliente();
    const dadosConta = this.clienteSessionService.getConta();

    if (dadosCliente && dadosConta) {
      this.cliente = dadosCliente;
      this.contaCliente = dadosConta;

      this.inicializarSaque();
    } else {
      this.router.navigate(['/login']);
    }
  }

  get saldoDisponivelTotal():number{
    return this.saldo + this.limite;
  }

  get valorASacar():number{
    
    return this.currencyFormatter.removeCurrencyMaskFromString(this.valorSaque);
  }

  get novoSaldo(): number{
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(this.valorSaque);

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
      title: 'Valor total a sacar',
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

  inicializarSaque(){
    this.saldo = this.contaCliente.saldo;
    this.limite = this.contaCliente.limite;    
  }

  handleValorSaque(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorSaque = input.value;
  }

  sacar() {

    const valor: number = this.currencyFormatter.removeCurrencyMaskFromString(this.valorSaque);

    if (valor <= 0) {
      this.mensagem = 'Valor inválido';
      return;
    }

    if (valor > this.saldoDisponivelTotal) {
      this.mensagem = 'Saldo insuficiente';
      return;
    }

    this.saldo = this.saldo - valor;
    
    
    this.valorSaque = '0,00';
    this.mensagem = 'Saque realizado com sucesso';
    this.corMensagem = 'green';
    
    
    this.contaCliente.saldo = this.saldo;
    
    
    this.contaService.atualizarConta(this.contaCliente);
    this.clienteSessionService.setContaCliente(this.contaCliente);
    
    this.registrarMovimentacao(valor);
    return;
  }

  registrarMovimentacao(valor: number){


    const movimentacao: Movimentacao = {
      id:0,
      data_hora: new Date(),
      tipo:'saque',
      clienteDestino: '',
      valor: valor,
      clienteOrigem: this.cliente.nome,
    }

    this.movimentacaoService.inserir(movimentacao);    

  }
}
