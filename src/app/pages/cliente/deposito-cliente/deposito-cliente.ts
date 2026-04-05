import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente, Conta, Movimentacao } from '../../../core/models/entities';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { Router } from '@angular/router';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { DecimalPipe } from '@angular/common';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';

@Component({
  selector: 'app-deposito-cliente',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './deposito-cliente.html',
  styleUrl: './deposito-cliente.css',
})
export class DepositoCliente implements OnInit {
  constructor(
    private router: Router,
    private contaService: ContaService,
    private clienteSessionService: ClienteSessionService,
    private movimentacaoService: MovimentacaoService
  ) {}

  private currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  saldo = 0;
  limite = 0;
  valorDeposito: string = '0,00';
  corMensagem = '';
  mensagem = '';

  get valorEstimadoDeposito(): number {
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorDeposito,
    );

    return this.saldo + valor;
  }
  cliente!: Cliente;
  contaCliente!: Conta;

  ngOnInit(): void {
    const dadosCliente = this.clienteSessionService.getCliente();
    const dadosConta = this.clienteSessionService.getConta();

    if (dadosCliente && dadosConta) {
      this.cliente = dadosCliente;
      this.contaCliente = dadosConta;

      this.inicializarDeposito();
    } else {
      this.router.navigate(['/login']);
    }
  }

  inicializarDeposito() {
    this.saldo = this.contaCliente.saldo;
    this.limite = this.contaCliente.limite;
  }

  handleValorDeposito(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorDeposito = input.value;
  }

  depositar() {
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorDeposito,
    );
    //user digita valor para deposito
    if (valor <= 0) {
      this.corMensagem = 'red';
      this.mensagem= 'O valor do depósito deve ser maior que zero'
      return;
    }

    this.saldo = this.saldo + valor;
    this.valorDeposito = '0,00';
    this.corMensagem= 'green';
    this.mensagem = 'Depósito realizado com sucesso';

    this.contaCliente.saldo = this.saldo;

    this.contaService.atualizarConta(this.contaCliente);
    this.clienteSessionService.setContaCliente(this.contaCliente);

    this.registrarMovimentacao(valor);
    return;
  }

  registrarMovimentacao(valor:number){

    const movimentacao: Movimentacao = {
      id:0,
      data_hora: new Date(),
      tipo:'deposito',
      clienteDestino: '',
      cpfClienteDestino: '',
      valor: valor,
      clienteOrigem: this.cliente.nome,
      cpfClienteOrigem: this.cliente.cpf,
    }

    this.movimentacaoService.inserir(movimentacao);    

  }
}
