import { Router } from '@angular/router';
import { Component } from '@angular/core';
import {NgxMaskDirective, NgxMaskPipe} from "ngx-mask";
import { FormsModule } from '@angular/forms';
import { Cliente } from '../../../core/models/entities';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { validateCEP, validateCPF, validateEmail } from '../../../core/shared/helpers';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective,FormsModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {
  constructor(private router: Router, private clienteService: ClienteService) {}

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  uf: string[] = [
    'AC', 'AL', 'AP', 'AM', 'BA',
    'CE', 'DF', 'ES', 'GO', 'MA',
    'MT','MS','MG','PA','PB','PR',
    'PE','PI','RJ','RN','RS','RO',
    'RR','SC','SP','SE','TO',
  ];

  cliente: Cliente = {
    id: 0,
    cpf: '',
    nome: '',
    email: '',
    telefone:'',
    senha: '',
    salario: 0,
    endereco: '',
  };

  cep: string = '';
  rua: string = '';
  cidade: string = '';
  estado: string = '';

  redirectToLoginPage() {
    this.router.navigate(['/login']);
  }

  salario: number = 0;

  handleSalario(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  registrarUsuario() {
    const isFieldsFilled = this.validateFields();

    this.cliente.nome = this.cliente.nome.trim();
    if(!isFieldsFilled){
      alert("Preencha todos os campos");
      return;
    }

    if (!validateEmail(this.cliente.email.trim())) {
      alert('Digite um email válido');
      return;
    }

    if(!validateCPF(this.cliente.cpf)){
      alert("Preencha o cpf corretamente");
      return;
    }

    if(!validateCEP(this.cep)){
      alert("Preencha o cep corretamente");
      return;
    }

    const validateRegister = this.clienteService.buscarClientePorEmail(this.cliente.email);

    if(validateRegister !== undefined){
      alert("Email já está em uso. Faça o login");
      return;
    }

    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;


    this.cliente.salario = this.salario;
    this.cliente.endereco = enderecoCompleto;


    this.clienteService.inserir(this.cliente);

    alert("Registro realizado com sucesso");

    this.router.navigate(['/cliente-main-page', this.cliente]);


  }

  validateFields():boolean{

    if(
      this.cliente.nome &&
      this.cliente.email &&
      this.cliente.senha &&
      this.cliente.cpf &&
      this.salario !== 0 &&
      this.cep &&
      this.rua &&
      this.cidade &&
      this.estado
    ) {
      return true;
    }

    return false;

  }
}
