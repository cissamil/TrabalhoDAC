import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {NgxMaskDirective, NgxMaskPipe} from "ngx-mask";
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { validateCEP, validateCPF, validateEmail } from '../../../core/shared/helpers';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { Cliente,} from '../../../core/models/entities';

@Component({
  selector: 'app-registro',
  imports: [NgxMaskDirective,FormsModule],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {
  constructor(
    private router: Router, 
    private contaService: ContaService,
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

  cliente: Cliente = {
    id: 0,
    cpf: '12345678910',
    nome: 'Peterson Fontinhas',
    email: 'petersonfontinhas@gmail.com ',
    telefone:'',
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

  registrarUsuario() {
    const verifyFileds = this.validateFields();

    if(verifyFileds != null){
      alert(verifyFileds);
      return;
    }
    
    this.cliente.nome = this.cliente.nome.trim();
    this.cliente.email = this.cliente.email.trim();

    const salario = this.currencyFormatter.removeCurrencyMaskFromString(this.salario);
    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;

    this.cliente.salario = salario;
    this.cliente.endereco = enderecoCompleto;

    this.clienteService.inserir(this.cliente);

    alert("Registro realizado com sucesso. Aguarde a aprovação da sua conta, você receberá um e-mail");

    this.router.navigate(['/login']);

  }

  validateFields(): string | null{

    if(!this.cliente.nome.trim()) return "Preencha o nome corretamente";
    
    if(!this.cliente.email.trim()) return "Preencha o email corretamente";

    if(!this.cliente.cpf) return "Preencha o CPF corretamente";

    if(this.salario === "0,00") return "Preencha o campo de salário";
    
    if(!this.cep) return "Preencha o o CEP corretamente";

    if(!this.rua) return "Preencha a rua corretamente";

    if(!this.cidade) return "Preencha a cidade corretamente";

    if(!this.estado) return "Preencha o estado corretamente";

    if (!validateEmail(this.cliente.email.trim())) return "Digite um email válido";

    if(!validateCPF(this.cliente.cpf)) return "Preencha o cpf corretamente";

    if(!validateCEP(this.cep)) return "Preencha o cep corretamente";

    const validateRegister = this.clienteService.buscarClientePorEmail(this.cliente.email);

    if(validateRegister !== undefined) return "Email já está em uso. Faça o login";
    
    return null;

  }


}
