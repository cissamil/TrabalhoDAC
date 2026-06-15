import { DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIcon } from "@angular/material/icon";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ContaCliente } from '../../../core/models/ContaGerente';
import { ContaSaque } from '../../../core/models/ContaSaque';
import { ResponseModal } from '../../../core/models/response-modal';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';

interface valorInfo {
  title: string;
  value: number;
  reference: string;
}

//ngModel
@Component({
  selector: 'app-saque-cliente',
  imports: [FormsModule, DecimalPipe, MatProgressSpinner, MatIcon],
  templateUrl: './saque-cliente.html',
  styleUrls: ['./saque-cliente.css', '../../shared/css/responseModal.css'],
})
export class SaqueCliente implements OnInit {
  constructor(
    private cdr: ChangeDetectorRef,
    private clienteService: ClienteService,
    private authService: AuthService,
    private contaService: ContaService,
    private compositionService: CompositionService,
  ) {}

  saldo = 0;
  limite = 0;
  valorSaque: string = '0,00';
  mensagem = '';
  corMensagem = '';

  private currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  conta!: ContaCliente;

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

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
    this.valorSaque = "0,00";
  }

  fillContaCliente(dadosCarregados: ClienteConta) {
    this.conta = dadosCarregados.conta;
    this.inicializarSaque();
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

  get saldoDisponivelTotal(): number {
    return this.saldo + this.limite;
  }

  get valorASacar(): number {
    return this.currencyFormatter.removeCurrencyMaskFromString(this.valorSaque);
  }

  get novoSaldo(): number {
    const valor = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorSaque,
    );

    return this.saldo - valor;
  }

  get listaValoresInfo(): valorInfo[] {
    return [
      {
        title: 'Saldo Atual',
        value: this.saldo,
        reference: 'saldoAtual',
      },
      {
        title: 'Limite Disponível',
        value: this.limite,
        reference: 'limite',
      },
      {
        title: 'Saldo Disponível Total',
        value: this.saldoDisponivelTotal,
        reference: 'saldoTotal',
      },
      {
        title: 'Valor total a sacar',
        value: this.valorASacar,
        reference: 'valorS',
      },
      {
        title: 'Novo Saldo (Previsão)',
        value: this.novoSaldo,
        reference: 'novoS',
      },
    ];
  }

  inicializarSaque() {
    this.saldo = this.conta.saldo;
    this.limite = this.conta.limite;
  }

  handleValorSaque(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.valorSaque = input.value;
  }

  sacar() {

    this.changeIsLoading();
    
    const token = this.authService.usuarioLogado;
    
    if (!token) {
      console.error("Token não encontrado!");
      return;
    }

    const valor: number = this.currencyFormatter.removeCurrencyMaskFromString(
      this.valorSaque,
    );

    if (valor <= 0) {
      this.mensagem = 'Valor inválido';
      this.corMensagem = 'red';
      return;
    }

    if (valor > this.saldoDisponivelTotal) {
      this.mensagem = 'Saldo insuficiente';
      this.corMensagem = 'red';
      
      this.changeIsLoading();
      return;
    }

    const contaSaque : ContaSaque = {
      contaNumber: this.conta.numeroConta,
      value: valor
    }


    this.contaService.sacarValor(contaSaque, token).subscribe({

      next: () => {

        
        setTimeout(() =>{
          
          this.getUpdatedClienteData();
          
          this.responseModal = {
            title: 'Saque concluido com sucesso!',
            message: `O saque no valor de R$ ${this.valorSaque} foi realizado com sucesso!`,
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
