import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {NgxMaskDirective, NgxMaskPipe} from "ngx-mask";
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { validateCEP, validateCPF, validateEmail } from '../../../core/shared/helpers';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { Cliente,} from '../../../core/models/entities';
import { MatIconModule } from '@angular/material/icon';
import { ResponseModal } from '../../../core/models/response-modal';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective, FormsModule, MatIconModule],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css', '../../shared/css/responseModal.css'],
})
export class Registro {
  constructor(
    private router: Router, 
    private clienteService: ClienteService, 
  ){}

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();

  uf: string[] = [
    'AC', 'AL', 'AP', 'AM', 'BA',
    'CE', 'DF', 'ES', 'GO', 'MA',
    'MT','MS','MG','PA','PB','PR',
    'PE','PI','RJ','RN','RS','RO',
    'RR','SC','SP','SE','TO',
  ];

  responseModal: ResponseModal | null = null;

  cliente: Cliente = {
    id: 0,
    cpf: '12345678910',
    nome: 'Peterson Fontinhas',
    email: 'petersonfontinhas@gmail.com',
    telefone:'41991455216',
    senha: '',
    salario: 0,
    endereco: '',
  };

  cep: string = '45645645';
  rua: string = 'flroes';
  cidade: string = 'cidades';
  estado: string = 'PR';

  redirectToLoginPage() {
    this.router.navigate(['/login']);
  }

  salario: string = "5.000,00";

  handleSalario(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  closeModal(){
    if(this.responseModal?.type === 'success'){
      this.router.navigate(['/login']);
    }

    this.responseModal = null;

  }

  registrarUsuario() {

    const verifyFields = this.validateFields();

    this.cliente.nome = this.cliente.nome.trim();
    this.cliente.email = this.cliente.email.trim();

    if(verifyFields != null){
      this.responseModal = {
        title: "Campo Inválido",
        message: verifyFields,
        messageIcon: "error",
        type: 'error'
      };
      return;
    }
    
    const salario = this.currencyFormatter.removeCurrencyMaskFromString(this.salario);
    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;

    this.cliente.salario = salario;
    this.cliente.endereco = enderecoCompleto;

    this.clienteService.inserir(this.cliente);

    this.responseModal = {
      title: "Registro realizado com sucesso!.",
      message: "Aguarde a aprovação da sua conta, você receberá um e-mail com a sua senha",
      messageIcon: "check",
      type: 'success'
    };
  }

  validateFields(): string | null{

    if(!this.cliente.nome) return "Preencha o nome corretamente";
    
    if(!this.cliente.email) return "Preencha o email corretamente";

    if(!this.cliente.cpf) return "Preencha o CPF corretamente";
    
    if(!this.cliente.telefone) return "Preencha o telefone corretamente";

    if(this.salario === "0,00") return "Preencha o campo de salário";
    
    if(!this.cep) return "Preencha o o CEP corretamente";

    if(!this.rua) return "Preencha a rua corretamente";

    if(!this.cidade) return "Preencha a cidade corretamente";

    if(!this.estado) return "Preencha o estado corretamente";


    if (!validateEmail(this.cliente.email)) return "Digite um email válido";

    if(!validateCPF(this.cliente.cpf)) return "Preencha o cpf corretamente";

    if(!validateCEP(this.cep)) return "Preencha o cep corretamente";

    const validateRegister = this.clienteService.buscarClientePorEmail(this.cliente.email);

    if(validateRegister !== undefined) return "Email já está em uso. Faça o login";
    
    return null;

  }


}
