import { Router } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';
import { FormsModule } from '@angular/forms';
import { DecimalPipe, NgClass } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ResponseModal } from '../../../core/models/response-modal';
import { validateCEP, validateEmail } from '../../../core/shared/helpers';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ContaCliente } from '../../../core/models/ContaGerente';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { ESTADOS_UF } from '../../../core/mock/estados-uf';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective, MatIconModule, NgClass, MatProgressSpinner],
  templateUrl: './cliente-perfil.html',
  styleUrls: ['./cliente-perfil.css', '../../shared/css/responseModal.css'],
})
export class ClientePerfil implements OnInit {
  constructor(
    //private router: Router,
    private clienteService: ClienteService,
    private compositionService: CompositionService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  private clienteConta!: ClienteConta;
  contaCliente!: ContaCliente;
  salario: string = '';
  updatedCliente!: ClienteConta;

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();
  responseModal: ResponseModal | null = null;

  uf: string [] = ESTADOS_UF;
  
  isLoading: boolean = false;

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

    this.clienteConta = dadosCarregados;
    this.contaCliente = dadosCarregados.conta;
    this.initalizeProfileData();
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

  get colorSaldo() {
    return this.contaCliente.saldo > 0 ? 'blue' : 'red';
  }

  initalizeProfileData() {
    this.updatedCliente = { ...this.clienteConta };

    this.salario = this.currencyFormatter.applyCurrencyMaskOnNumber(
      this.clienteConta.cliente.salario,
    );
  }

  handleSalario(e: any) {
    //mascara em tempo real
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  closeModal() {
    this.responseModal = null;
  }

  atualizarDados() {

    this.changeIsLoading();

    const verifyFields = this.validateFields();

    const token = this.authService.usuarioLogado;
    
    if (!token) {
      console.error("Token não encontrado!");
      return;
    }

    const salarioNumber: number = this.currencyFormatter.removeCurrencyMaskFromString(this.salario);

    this.updatedCliente.cliente.salario = salarioNumber;

    if (verifyFields != null) {
      this.responseModal = {
        title: 'Campo Inválido',
        message: verifyFields,
        messageIcon: 'error',
        type: 'error',
      };

      this.changeIsLoading();
      return;
    }

    this.clienteService.atualizar(this.updatedCliente.cliente, token).subscribe({
      next: () => {

        setTimeout(() =>{
          
          this.getUpdatedClienteData();
          
          this.responseModal = {
            title: 'Dados Atualizados!',
            message: 'Suas informações e limites foram recalculados com sucesso.',
            messageIcon: 'check',
            type: 'success',
          };

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


  validateFields(): string | null {
    if (!this.updatedCliente.cliente.nome) return 'Preencha o nome corretamente';

    if (!this.updatedCliente.cliente.email)
      return 'Preencha o email corretamente';

    if (!this.updatedCliente.cliente.cpf) return 'Preencha o CPF corretamente';

    if (!this.updatedCliente.cliente.telefone)
      return 'Preencha o telefone corretamente';

    if (this.salario === '0,00') return 'Preencha o campo de salário';

    if (!this.updatedCliente.cliente.endereco.cep) return 'Preencha o o CEP corretamente';

    if (!this.updatedCliente.cliente.endereco.logradouro) return 'Preencha a rua corretamente';

    if (!this.updatedCliente.cliente.endereco.numero) return 'Preencha o número da rua corretamente';

    if (!this.updatedCliente.cliente.endereco.cidade) return 'Preencha a cidade corretamente';

    if (!this.updatedCliente.cliente.endereco.estado) return 'Preencha o estado corretamente';

    if (!validateEmail(this.clienteConta.cliente.email)) return 'Digite um email válido';

    if (!validateCEP(this.updatedCliente.cliente.endereco.cep)) return 'Preencha o cep corretamente';

    return null;
  }
}
