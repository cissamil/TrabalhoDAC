import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { DecimalPipe } from '@angular/common';
import { NgxMaskPipe } from 'ngx-mask';
import { ClientTableData } from '../../../core/models/table-data';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { Cliente, Conta, GerenteAdmin } from '../../../core/models/entities';


@Component({
  selector: 'app-admin-relatorio-clientes',
  imports: [MatIconModule, DecimalPipe, NgxMaskPipe, MatTableModule],
  templateUrl: './admin-relatorio-clientes.html',
  styleUrl: './admin-relatorio-clientes.css',
})
export class AdminRelatorioClientes implements OnInit {

  constructor(
    private gerenteService: GerenteService,
    private contaService: ContaService,
    private clienteService: ClienteService
  ) {}

  gerentes: GerenteAdmin[] = [];
  contas: Conta[] =[];
  clientes: Cliente[] =[];

  CLIENTS_TABLE: ClientTableData[] = [];
  displayedColumns: string[] = [
    'CPF Cliente',
    'Nome Cliente',
    'E-mail',
    'Salario',
    'N Conta',
    'Saldo',
    'Limite',
    'CPF Gerente',
    'Nome Gerente',
  ];



  ngOnInit(): void {
    this.listarClientes();
    this.contas = this.contaService.listarTodos();
    this.listarGerentes();
    console.log('clientes do service', this.clientes);
  console.log('contas do service', this.contas);
  console.log('gerentes do service', this.gerentes);
    this.fillClientsTable();
  }

  listarClientes():void{
    this.clienteService.listarTodos().subscribe({
      next: (clientes: Cliente[]) => {
      this.clientes = clientes;
    },
    error: (erro) => {
      console.log('Erro ao listar clientes', erro);
      this.clientes = [];
    }
    })
  }

  listarGerentes():void{
    this.gerenteService.listarTodos().subscribe({
      next: (gerentes: GerenteAdmin[]) => {
      this.gerentes = gerentes;
    },
    error: (erro) => {
      console.log('Erro ao listar gerentes', erro);
      this.gerentes = [];
    }
    })
  }

  fillClientsTable() {
  this.CLIENTS_TABLE = [];

  console.log('CLIENTES:', this.clientes);
  console.log('CONTAS:', this.contas);
  console.log('GERENTES:', this.gerentes);

  this.clientes.forEach((cliente) => {
    const contaCliente = this.contas.find(
      (conta) => conta.cpfCliente === cliente.cpf
    );

    const gerenteCliente = this.gerentes.find(
      (gerente) => gerente.cpf === contaCliente?.cpfGerente
    );

    console.log('cliente atual:', cliente.nome, cliente.cpf);
    console.log('conta encontrada:', contaCliente);
    console.log('gerente encontrado:', gerenteCliente);

    if (contaCliente && gerenteCliente) {
      this.CLIENTS_TABLE.push({
        cpfCliente: cliente.cpf,
        nomeCliente: cliente.nome,
        emailCliente: cliente.email,
        salarioCliente: cliente.salario,
        numeroContaCliente: contaCliente.numeroConta,
        saldoContaCliente: contaCliente.saldo,
        limiteContaCliente: contaCliente.limite,
        cpfGerente: gerenteCliente.cpf,
        nomeGerente: gerenteCliente.nome,
        colorSaldo: contaCliente.saldo >= 0 ? 'green' : 'red'
      });
    }
  });

  console.log('TABELA FINAL:', this.CLIENTS_TABLE);

  this.CLIENTS_TABLE.sort((a, b) => a.nomeCliente.localeCompare(b.nomeCliente));
}
}
