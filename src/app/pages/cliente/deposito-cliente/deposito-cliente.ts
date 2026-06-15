import { DecimalPipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
// import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { MatIcon } from '@angular/material/icon';
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ResponseModal } from '../../../core/models/response-modal';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ContaDeposito } from '../../../core/models/ContaDeposito';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ContaCliente } from '../../../core/models/ContaGerente';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';

@Component({
  selector: 'app-deposito-cliente',
  imports: [FormsModule, DecimalPipe, MatIcon, MatProgressSpinner],
  templateUrl: './deposito-cliente.html',
  styleUrls: ['./deposito-cliente.css', '../../shared/css/responseModal.css'],
})
export class DepositoCliente implements OnInit {
  constructor(
    private cdr: ChangeDetectorRef,
    private clienteService: ClienteService,
    private contaService: ContaService,
    private authService: AuthService,
    private compositionService: CompositionService
  ) {}

  private currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

  saldo = 0;
  limite = 0;
  valorDeposito: string = '0,00';
  corMensagem = '';
  mensagem = '';

  conta!: ContaCliente;

  closeModal() {
    this.responseModal = null;
  }

  changeIsLoading(){
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

  fillContaCliente(dadosCarregados: ClienteConta){

    this.conta = dadosCarregados.conta;
    this.inicializarDeposito();
  }

  ngOnInit(): void {

    this.isLoading = true;
    
    const dadosCarregados = this.clienteService.clienteContaLogado;

    if (dadosCarregados) {
      this.fillContaCliente(dadosCarregados);

    } else {
      console.log('Nenhum dado encontrado no localStorage para o Perfil.');
    }

    this.isLoading = false
  }

  get valorEstimadoDeposito(): number {
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorDeposito,
    );
    return this.saldo + valor;
  }

  inicializarDeposito() {

    this.saldo = this.conta.saldo;
    this.limite = this.conta.limite;
    this.cdr.detectChanges();
  }

  handleValorDeposito(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorDeposito = input.value;
  }

  cleanInput(){
    this.valorDeposito = "0,00";
  }

  depositar() {

    this.changeIsLoading();

    const token = this.authService.usuarioLogado;
    
    if (!token) {
      console.error("Token não encontrado!");
      return;
    }

    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorDeposito,
    );

    if (valor <= 0) {
      this.corMensagem = 'red';
      this.mensagem = 'O valor do depósito deve ser maior que zero';
      
      this.changeIsLoading();
      return;
    }

    const contaDeposito: ContaDeposito = {
      contaNumber: this.conta.numeroConta,
      value: valor
    }

    this.contaService.depositarValor(contaDeposito, token).subscribe({

      next: () => {

        
        setTimeout(() =>{
          
          this.getUpdatedClienteData();
          
          this.responseModal = {
            title: 'Depósito concluido com sucesso!',
            message: `O depósito no valor de ${this.valorDeposito} foi realizado com sucesso!`,
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
          title: backendError?.error || 'Erro na atualização de dados',
          message:
            backendError?.message ||
            'Ocorreu um erro ao tentar atualizar seus dados. Tente novamente.',
          messageIcon: 'error',
          type: 'error',
        };

        this.changeIsLoading();
      },
    });
  }
}
