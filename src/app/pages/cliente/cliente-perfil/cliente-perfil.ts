import { Router } from '@angular/router';
import { NgxMaskDirective } from "ngx-mask";
import { FormsModule } from "@angular/forms";
import { DecimalPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Component, Input, OnInit } from '@angular/core';
import { Cliente, Conta } from '../../../core/models/entities';
import { ResponseModal } from '../../../core/models/response-modal';
import { validateCEP, validateEmail } from '../../../core/shared/helpers';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteSessionService } from '../../../core/services/session-controller.service';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective, MatIconModule],
  templateUrl: './cliente-perfil.html',
  styleUrls: ['./cliente-perfil.css', '../../shared/css/responseModal.css'],
})
export class ClientePerfil implements OnInit {
  constructor(
    private clienteSessionService: ClienteSessionService,
    private router: Router,
    private clienteService: ClienteService,
  ) {}

  private cliente!: Cliente;
  conta_cliente!: Conta;
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
      this.conta_cliente = dadosConta;

      this.initalizeProfileData();
    } else {
      this.router.navigate(['/login']);
    }
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

    const salarioNumber: number =
      this.currencyFormatter.removeCurrencyMaskFromString(this.salario);

    console.log('Salário atual', salarioNumber);

    const enderecoCompleto = `${this.cep} - ${this.rua} - ${this.cidade} - ${this.estado}`;

    this.updatedCliente.salario = salarioNumber;
    this.updatedCliente.endereco = enderecoCompleto;

    this.clienteService.atualizar(this.updatedCliente);
    this.clienteSessionService.setCliente(this.updatedCliente);

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
