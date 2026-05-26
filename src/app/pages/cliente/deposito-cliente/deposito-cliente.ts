import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente, Conta, Movimentacao } from '../../../core/models/entities';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { Router } from '@angular/router';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { DecimalPipe } from '@angular/common';
// import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ContaGerente } from '../../../core/models/ContaGerente';

@Component({
  selector: 'app-deposito-cliente',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './deposito-cliente.html',
  styleUrl: './deposito-cliente.css',
})
export class DepositoCliente implements OnInit {
  constructor(
  private cdr: ChangeDetectorRef,
  private clienteService: ClienteService,
  private contaService: ContaService,
  private movimentacaoService: MovimentacaoService) {}

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

  clienteConta!: ClienteConta;

  ngOnInit(): void {
    const dadosCarregados = this.clienteService.clienteContaLogado;

    if (dadosCarregados) {
      this.clienteConta = dadosCarregados;
      this.inicializarDeposito();
    } else {
      console.log("Nenhum dado de cliente e conta encontrado no localStorage.");
    }
  }

  inicializarDeposito() {
    const conta= this.clienteConta.conta;
    this.saldo = conta.saldo;
    this.limite = conta.limite;
    this.cdr.detectChanges();
  }

  handleValorDeposito(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorDeposito = input.value;
  }

  depositar() {
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorDeposito
    );
    //user digita valor para deposito
    if (valor <= 0) {
      this.corMensagem = 'red';
      this.mensagem= 'O valor do depósito deve ser maior que zero'
      return;
    }
//criado para mostrar como seria se ele depossitasse aquele valor
const contaAtualizada: ContaGerente={
    ...this.clienteConta.conta,
    saldo: this.saldo + valor
}
this.contaService.atualizarConta(contaAtualizada as any).subscribe({
      next: (contaBanco: any) => {
    const contaConvertida = contaBanco as ContaGerente;
        // atualiza a tela com dado real
        this.clienteConta.conta = contaBanco;
        this.clienteService.setClienteConta(this.clienteConta);
        this.valorDeposito = '0,00';
        this.corMensagem = 'green';
        this.mensagem = 'Depósito realizado com sucesso';
        this.registrarMovimentacao(valor);
        this.cdr.detectChanges();
      },
      error: (erro) => {
        console.error('Erro ao efetuar depósito', erro);
        this.corMensagem = 'red';
        this.mensagem = 'Erro ao processar o depósito no servidor';
      }
    });
  }
    //this.contaCliente.saldo = this.saldo;
    //this.contaService.atualizarConta(this.contaCliente);


  registrarMovimentacao(valor:number){
    const movimentacao: Movimentacao = {
          id:0,
          data_hora: new Date(),
          tipo:'deposito',
          clienteDestino: '',
          cpfClienteDestino: '',
          valor: valor,
          clienteOrigem: this.clienteConta.nome,
          cpfClienteOrigem: this.clienteConta.cpf,
        }

    this.movimentacaoService.inserir(movimentacao).subscribe({
      next:(movimentacaoSalva)=>{
        console.log('Movimentação registrada com sucesso')
      },
      error: (erro)=>{
        console.error(' erro ao registrar movimentação no extrato ', erro);
      }
    })
  }
}
