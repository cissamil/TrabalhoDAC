import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { Endereco } from '../../../core/models/EnderecoEntity';
import { ResponseModal } from '../../../core/models/response-modal';
import { Cliente, ClienteOutdated } from '../../../core/models/entities';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import {validateCEP, validateCPF, validateEmail} from '../../../core/shared/helpers';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective, FormsModule, MatIconModule],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css', '../../shared/css/responseModal.css'],
})
export class Registro implements OnInit {
  constructor(
    private router: Router,
    private clienteService: ClienteService,
  ) {}

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

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

  cep: string = '';
  rua: string = '';
  cidade: string = '';
  estado: string = '';
  numero: number = 0;

  ngOnInit(): void {
    //this.redirectToLoginPage();
  }

  redirectToLoginPage() {
    this.router.navigate(['/login']);
  }

  salario: string = '';

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

    const endereco : Endereco = {
      id: 0,
      cep: this.cep,
      logradouro: this.rua,
      cidade: this.cidade,
      estado: this.estado,
      numero: this.numero,

    };

    this.cliente.nome = this.cliente.nome.trim();
    this.cliente.email = this.cliente.email.trim();
    this.cliente.salario = Number(
      this.currencyFormatter.removeCurrencyMaskFromString(this.salario),
    );


    this.cliente.endereco = endereco;

    this.clienteService.inserir(this.cliente).subscribe({
      next: (clienteCriado) => {
        this.responseModal = {
          title: 'Registro realizado com sucesso!',
          message:
            'Aguarde a aprovação da sua conta, você receberá um e-mail com a sua senha.',
          messageIcon: 'check',
          type: 'success',
        };
      },
      error: (erro) => {
        console.error(erro);
        this.responseModal = {
          title: 'Erro no Cadastro',
          message:
            erro.error?.message ||
            'Ocorreu um erro ao tentar se cadastrar. Tente novamente.',
          messageIcon: 'error',
          type: 'error',
        };
      },
    });
  }

  validateFields(): string | null {
    if (!this.cliente.nome) return 'Preencha o nome corretamente';

    if (!this.cliente.email) return 'Preencha o email corretamente';

    if (!this.cliente.cpf) return 'Preencha o CPF corretamente';

    if (!this.cliente.telefone) return 'Preencha o telefone corretamente';

    if (this.salario === '0,00') return 'Preencha o campo de salário';

    if (!this.cep) return 'Preencha o o CEP corretamente';

    if (!this.rua) return 'Preencha a rua corretamente';

    if (!this.cidade) return 'Preencha a cidade corretamente';

    if (!this.estado) return 'Preencha o estado corretamente';

    if (!validateEmail(this.cliente.email)) return 'Digite um email válido';

    if (!validateCPF(this.cliente.cpf)) return 'Preencha o cpf corretamente';

    if (!validateCEP(this.cep)) return 'Preencha o cep corretamente';

    return null;
  }
}
