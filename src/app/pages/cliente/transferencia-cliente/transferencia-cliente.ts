import { DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIcon } from "@angular/material/icon";
import { HttpErrorResponse } from '@angular/common/http';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ContaCliente } from '../../../core/models/ContaGerente';
import { ResponseModal } from '../../../core/models/response-modal';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ContaTransferencia } from '../../../core/models/ContaTransferencia';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { MatProgressSpinner } from "@angular/material/progress-spinner";


interface valorInfo{
  title:string;
  value:number;
  reference:string;
}

@Component({
  selector: 'app-transferencia-cliente',
  imports: [FormsModule, DecimalPipe, MatIcon, MatProgressSpinner],
  templateUrl: './transferencia-cliente.html',
  styleUrls: ['./transferencia-cliente.css', '../../shared/css/responseModal.css'],
})
export class TransferenciaCliente implements OnInit{

  constructor(
    private cdr: ChangeDetectorRef,
    private clienteService: ClienteService,
    private authService: AuthService,
    private contaService: ContaService,
    private compositionService: CompositionService,
  ) {}

  numeroContaDestino: string = "";
  valorTransferencia: string = '0,00';
  mensagem = "";
  corMensagem = "";
  saldo = 0;
  limite = 0;
  tipoErro = '';

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;
  
  private currencyFormatter = new CurrencyFormatter();

  conta!: ContaCliente;

  closeModal() {
      this.responseModal = null;
    }
  
    changeIsLoading() {
      this.isLoading = !this.isLoading;
      this.cdr.detectChanges();
    }
  
    getUpdatedClienteData() {
      const token = this.authService.usuarioLogado;
      if (!token) {
        return;
      }
  
      this.compositionService.getClienteConta(token).subscribe({
        next: (responseBody) => {
          if (responseBody) {
            this.clienteService.setClienteConta(responseBody);
  
            this.fillContaCliente(responseBody);
          }
        },
      });
    }
  
    cleanInput(){
      this.valorTransferencia = "0,00";
      this.numeroContaDestino = "";
    }
  
    fillContaCliente(dadosCarregados: ClienteConta) {
      this.conta = dadosCarregados.conta;
      this.inicializarTransferencia();
    }
  
    ngOnInit(): void {
      this.isLoading = true;
  
      const dadosCarregados = this.clienteService.clienteContaLogado;
  
      if (dadosCarregados) {
        this.fillContaCliente(dadosCarregados);
      } else {
        console.log('Nenhum dado encontrado no localStorage para o Perfil.');
      }
  
      this.isLoading = false;
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
    this.saldo = this.conta.saldo;
    this.limite = this.conta.limite;
  }

  handleValorTransferencia(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorTransferencia = input.value;
  }


  transferir(){

    this.changeIsLoading();

    this.mensagem = ''
    this.tipoErro = '';
    this.corMensagem = '';

    const token = this.authService.usuarioLogado;
    
    if (!token) {
      console.error("Token não encontrado!");
      return;
    }

    const valor = this.currencyFormatter.removeCurrencyMaskFromString(this.valorTransferencia);

    if(this.numeroContaDestino === this.conta.numeroConta){
      this.mensagem = "Você não pode transferir para você mesmo";
      this.tipoErro = 'erroCONTA';
      this.corMensagem = 'red';
      this.changeIsLoading();
      return;
    }

    if(this.numeroContaDestino.length < 4){
      this.mensagem = "Preencha a conta corretamente (4 dígitos)";
      this.tipoErro = 'erroCONTA';
      this.corMensagem = 'red';
      this.changeIsLoading();
      return;
    }

    if(valor <= 0){
      this.mensagem="Valor inválido"
      this.tipoErro = 'erroTR'
      this.corMensagem = 'red';
      this.changeIsLoading();
      return;
    }

    if(valor > (this.saldo+this.limite)){
      this.mensagem="Saldo insuficiente"
      this.tipoErro = 'erroTR';
      this.corMensagem = 'red';
      this.changeIsLoading();
      return;
    }

    const contaTransferencia: ContaTransferencia = {
      originContaNumber: this.conta.numeroConta,
      destinyContaNumber: this.numeroContaDestino,
      value: valor
    }
    this.contaService.transferirValor(contaTransferencia, token).subscribe({

      next: () => {

        setTimeout(() =>{
          
          this.getUpdatedClienteData();
          
          this.responseModal = {
            title: 'Transferência concluida com sucesso!',
            message: `A transferência para a conta ${this.numeroContaDestino} no valor de R$ ${this.valorTransferencia} foi realizado com sucesso!`,
            messageIcon: 'check',
            type: 'success',
          };
          
          this.cleanInput();
          this.changeIsLoading();
          
        }, 800)
      },
      error: (erro: HttpErrorResponse) => {

        console.error("Erro Interceptado: ", erro);

        const backendError = erro.error as StandartErrorResponse;

        this.responseModal = {
          title: backendError?.error || 'Erro ao processar requisição',
          message:
            backendError?.message ||
            'Ocorreu um erro ao processar sua requisição. Tente novamente',
          messageIcon: 'error',
          type: 'error',
        };

        this.changeIsLoading();
      },
    });
  }

}



