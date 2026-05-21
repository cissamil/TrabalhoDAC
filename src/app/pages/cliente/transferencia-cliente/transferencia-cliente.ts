import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente, Conta, Movimentacao } from '../../../core/models/entities';
import { Router } from '@angular/router';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { DecimalPipe } from '@angular/common';
import { error } from 'console';


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
    private movimentacaoService: MovimentacaoService
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

    this.mensagem = ''
    this.tipoErro = '';
    this.corMensagem = '';

    const valor = this.currencyFormatter.removeCurrencyMaskFromString(this.valorTransferencia);

    if(Number(this.numeroContaDestino) === this.contaCliente.numeroConta){
      this.mensagem = "Você não pode transferir para você mesmo";
      this.tipoErro = 'erroCONTA';
      this.corMensagem = 'red';
      return;
    }

    if(this.numeroContaDestino.length < 4){
      this.mensagem = "Preencha a conta corretamente (4 dígitos)";
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

    const transferencia: Movimentacao = {
      id: 0,
      data_hora: new Date(),
      tipo: 'transferencia',
      valor: valor,
      clienteOrigem: this.cliente.nome,
      cpfClienteOrigem: this.cliente.cpf,
      clienteDestino: '',
      cpfClienteDestino: this.numeroContaDestino
    };

    this.movimentacaoService.inserir(transferencia).subscribe({
      next: () => {
        this.corMensagem = 'green';
        this.mensagem = "Transferência realizada com sucesso";
        this.valorTransferencia = "0,00";
        this.numeroContaDestino = "";
        this.recarregarContaCliente();
      },
      error: (erro:any) => {
        console.error("Erro na transferência via REST:", erro);
        this.corMensagem = 'red';
        // Se o back-end retornar 404, significa que a conta destino não existe
        if (erro.status === 404) {
          this.mensagem = 'Conta de destino não encontrada';
          this.tipoErro = 'erroCONTA';
        } else {
          this.mensagem = 'Transferência não realizada. Erro no servidor.';
          this.tipoErro = 'erroTR';
        }
      }
    });
  }
  private recarregarContaCliente() {
    this.contaService.buscarPorId(this.contaCliente.id).subscribe({
      next: (contaAtualizada: Conta) => {
        this.saldo = contaAtualizada.saldo;
        this.contaCliente = contaAtualizada;
        this.clienteSessionService.setContaCliente(contaAtualizada); // Sincroniza a sessão local
      }
    });
  }
}

    // const contaDestino = this.contaService.buscarPorNumeroConta(this.numeroContaDestino).subscribe({
    //   next:(contaDestino:any)=>{
    //     this.contaService.realizarTransferencia(contaOrigem, contaDestino, valor).subscribe({
    //     const contaOrigem = this.contaCliente;
    //       next:()=>{
    //       this.saldo=contaBanco.saldo;
    //       this.contaOrigem=contaBanco;
    //       this.valorTransferencia = "0,00";
    //       this.numeroContaDestino = "";
    //       this.corMensagem = 'green';
    //       this.mensagem = "Transferência realizada com sucesso";
    //       console.log("Conta de destino: ", contaDestino);
    //   },
    //   error: (erro:any) =>{
    //     this.mensagem = 'Transferência não realizada';
    //     this.tipoErro = 'erroTR';
    //     this.corMensagem = 'red';
    //     console.error("Transferência não realizada", erro);
    //   }

    // },
    //   error: (erro:any) =>{
    //     this.mensagem = 'Conta de desino não encontrada';
    //     this.tipoErro = 'erroCONTA';
    //     this.corMensagem = 'red';
    //     console.error("Conta não encontrada", erro);
    //   }
    // });

    // console.log("Conta de destino: ", contaDestino);
    // if(!contaDestino){
    //   this.mensagem = 'Conta de desino não encontrada';
    //   this.tipoErro = 'erroCONTA';
    //   this.corMensagem = 'red';

    //   console.error("Conta não encontrada");
    //   return;
    // }

    //contaDestino.saldo += valor;


