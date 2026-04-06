import { Router } from '@angular/router';
import { NgxMaskDirective } from "ngx-mask";
import { FormsModule } from "@angular/forms";
import { DecimalPipe, NgClass } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Component, Input, OnInit } from '@angular/core';
import { Cliente, Conta } from '../../../core/models/entities';
import { ResponseModal } from '../../../core/models/response-modal';
import { validateCEP, validateEmail } from '../../../core/shared/helpers';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { ContaService } from '../../../core/services/conta-services/conta-service';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective, MatIconModule, NgClass],
  templateUrl: './cliente-perfil.html',
  styleUrls: ['./cliente-perfil.css', '../../shared/css/responseModal.css'],
})
export class ClientePerfil implements OnInit {
  constructor(
    private router: Router,
    private contaService: ContaService,
    private clienteService: ClienteService,
    private clienteSessionService: ClienteSessionService,
  ) {}

  private cliente!: Cliente;
  contaCliente!: Conta;
  cep: string = '';
  rua: string = '';
  cidade: string = '';
  estado: string = '';
  endereco: string[] = [];
  salario: string = '';
  updatedCliente!: Cliente;

  currencyFormatter: CurrencyFormatter = new CurrencyFormatter();
  responseModal: ResponseModal | null = null;

  ngOnInit(): void {
    const dadosCliente = this.clienteSessionService.getCliente();
    const dadosConta = this.clienteSessionService.getConta();

    if (dadosCliente && dadosConta) {
      this.cliente = dadosCliente;
      this.contaCliente = dadosConta;

      this.initalizeProfileData();
    } else {
      this.router.navigate(['/login']);
    }
  }

  get colorSaldo(){
    return this.contaCliente.saldo > 0 ? 'blue' : 'red';
  }

  initalizeProfileData() {
    this.updatedCliente = this.cliente;
    const enderecoParts = this.cliente.endereco.split(' - ');
    this.cep = enderecoParts[0] || '';
    this.rua = enderecoParts[1] || '';
    this.cidade = enderecoParts[2] || '';
    this.estado = enderecoParts[3] || '';

    // Formata o salário para exibição inicial
    this.salario = this.currencyFormatter.applyCurrencyMaskOnNumber(
      this.cliente.salario,
    );
  }

  handleSalario(e: any) {
    let input = e.target;
    input.value = this.currencyFormatter.applyCurrencyMaskOnString(input.value);
    this.salario = input.value;
  }

  closeModal(){
    this.responseModal = null;
  }


  atualizarDados() {

    const verifyFields = this.validateFields();

    if(verifyFields != null){
      this.responseModal = {
        title: "Campo Inválido",
        message: verifyFields,
        messageIcon: "error",
        type: 'error'
      };
      return;
    }

    const salarioNumber: number = this.currencyFormatter.removeCurrencyMaskFromString(this.salario);
    let novoLimite = salarioNumber / 2;

    console.log(`Novo limite: ${novoLimite}, Saldo: ${this.contaCliente.saldo}`)

    if(this.contaCliente.saldo < 0){

      const saldoPositivo = this.contaCliente.saldo * -1;
      
      if(novoLimite < saldoPositivo){
        novoLimite = saldoPositivo;
      }
    }

    this.contaCliente.limite = novoLimite;

    console.log('Salário atual', salarioNumber);

    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;

    this.updatedCliente.salario = salarioNumber;
    this.updatedCliente.endereco = enderecoCompleto;

    this.clienteService.atualizar(this.updatedCliente);
    this.clienteSessionService.setCliente(this.updatedCliente);
    this.clienteSessionService.setContaCliente(this.contaCliente);

    this.contaService.atualizarConta(this.contaCliente);
  

    this.responseModal = {
      title: "Sucesso",
      message: "Dados alterados com êxito!",
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

    if(!validateCEP(this.cep)) return "Preencha o cep corretamente";

    
    return null;

  }

}
