import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { ResponseModal } from '../../../core/models/response-modal';
import { Cliente, ClienteOutdated } from '../../../core/models/entities';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import {validateCEP, validateCPF, validateEmail} from '../../../core/shared/helpers';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective, FormsModule, MatIconModule, MatProgressSpinner],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css', '../../shared/css/responseModal.css'],
})
export class Registro{
  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private cdr: ChangeDetectorRef
  ) {}

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();
  isLoading: boolean = false;


  uf: string[] = [
    'AC', 
    'AL',
    'AP',
    'AM',
    'BA',
    'CE',
    'DF',
    'ES',
    'GO',
    'MA',
    'MT',
    'MS',
    'MG',
    'PA',
    'PB',
    'PR',
    'PE',
    'PI',
    'RJ',
    'RN',
    'RS',
    'RO',
    'RR',
    'SC',
    'SP',
    'SE',
    'TO',
  ];

  responseModal: ResponseModal | null = null;

  cliente: Cliente = {
    clienteId: '',
    cpf: '',
    nome: '',
    email: '',
    telefone: '',
    salario: 0,
    endereco: {
      id: 0,
      numero: 0,
      cep: '',
      cidade: '',
      estado: '',
      logradouro: '',
    },
  };

  redirectToLoginPage() {
    this.router.navigate(['/login']);
  }

  changeIsLoading(){
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

  salario: string = '0,00';

  handleSalario(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  closeModal() {
    if (this.responseModal?.type === 'success') {
      this.router.navigate(['/login']);
    }

    this.responseModal = null;
  }

  registrarUsuario() {
    this.changeIsLoading();

    const validationMessage = this.validateFields();
    if (validationMessage) {
      this.responseModal = {
        title: 'campo inválido',
        message: validationMessage,
        messageIcon: 'error',
        type: 'error',
      };

      return;
    }

    this.cliente.nome = this.cliente.nome.trim();
    this.cliente.email = this.cliente.email.trim();
    this.cliente.salario = Number(
      this.currencyFormatter.removeCurrencyMaskFromString(this.salario),
    );


    this.clienteService.inserir(this.cliente).subscribe({
      next: () => {
        console.log("Entrou no bloco next");

        this.responseModal = {
          title: 'Registro realizado com sucesso!',
          message:
            'Aguarde a aprovação da sua conta, você receberá um e-mail com a sua senha.',
          messageIcon: 'check',
          type: 'success',
        };

        this.changeIsLoading();      },
      error: (erro: HttpErrorResponse) => {

        console.log("Entrou no bloco error");

        console.error("Erro Interceptado: ", erro);

        const backendError = erro.error as StandartErrorResponse;

        this.responseModal = {
          title: backendError?.error || 'Erro no Cadastro',
          message:
            backendError?.message ||
            'Ocorreu um erro ao tentar se cadastrar. Tente novamente.',
          messageIcon: 'error',
          type: 'error',
        };

        this.changeIsLoading();
      },
    });


  }

  validateFields(): string | null {
    if (!this.cliente.nome) return 'Preencha o nome corretamente';

    if (!this.cliente.email) return 'Preencha o email corretamente';

    if (!this.cliente.cpf) return 'Preencha o CPF corretamente';

    if (!this.cliente.telefone) return 'Preencha o telefone corretamente';

    if (this.salario === '0,00') return 'Preencha o campo de salário';

    if (!this.cliente.endereco.cep) return 'Preencha o o CEP corretamente';

    if (!this.cliente.endereco.logradouro) return 'Preencha a rua corretamente';

    if (!this.cliente.endereco.numero) return 'Preencha o número da residência corretamente';

    if (!this.cliente.endereco.cidade) return 'Preencha a cidade corretamente';

    if (!this.cliente.endereco.estado) return 'Preencha o estado corretamente';

    if (!validateEmail(this.cliente.email)) return 'Digite um email válido';

    if (!validateCPF(this.cliente.cpf)) return 'Preencha o cpf corretamente';

    if (!validateCEP(this.cliente.endereco.cep)) return 'Preencha o cep corretamente';

    return null;
  }
}
