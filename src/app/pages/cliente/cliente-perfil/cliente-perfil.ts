import { Router } from '@angular/router';
import { NgxMaskDirective } from "ngx-mask";
import { FormsModule } from "@angular/forms";
import { DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Cliente, Conta } from '../../../core/models/entities';
import { validateCEP, validateEmail } from '../../../core/shared/helpers';
import { CurrencyFormatter } from '../../../core/shared/currency_formatter';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ClienteSessionService } from '../../../core/services/session-controller.service';

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective],
  templateUrl: './cliente-perfil.html',
  styleUrl: './cliente-perfil.css',
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

  atualizarDados() {

    if (!this.validateFields) {
      alert('Preencha todos os campos');
      return;
    }
    if (!validateEmail(this.updatedCliente.email)) {
      alert('Digite um email válido');
      return;
    }

    if (!validateCEP(this.cep.trim())) {
      alert('Preencha o CEP corretamente');
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

    alert("Dados alterados com sucesso!");
  }

  validateFields(): boolean {
    if (
      this.cliente.nome &&
      this.cliente.email &&
      this.cliente.senha &&
      this.cliente.cpf &&
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
