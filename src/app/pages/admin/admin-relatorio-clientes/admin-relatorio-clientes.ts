import { DecimalPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { NgxMaskPipe } from 'ngx-mask';
import { CLIENTES_MOCK, CONTAS_MOCK, STAFF_MOCK } from '../../../core/mock/mock-data';
import { ClientTableData } from '../../../core/models/table-data';

@Component({
  selector: 'app-admin-relatorio-clientes',
  imports: [MatIconModule, DecimalPipe, NgxMaskPipe, MatTableModule],
  templateUrl: './admin-relatorio-clientes.html',
  styleUrl: './admin-relatorio-clientes.css',
})
export class AdminRelatorioClientes implements OnInit {
  gerentes = STAFF_MOCK.filter((gerente) => gerente.tipo == 'gerente');
  contas = CONTAS_MOCK;
  clientes = CLIENTES_MOCK;

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
    this.fillClientsTable();
  }

  fillClientsTable() {

    this.clientes.forEach((cliente) =>{
      const contaCliente = this.contas.find((conta) => conta.cliente == cliente.nome);
      const gerenteCliente = this.gerentes.find((gerente) => gerente.nome == contaCliente?.gerente)

      if(contaCliente && gerenteCliente){

        this.CLIENTS_TABLE.push({
  
          cpfCliente: cliente.cpf,
          nomeCliente:cliente.nome,
          emailCliente:cliente.email,
          salarioCliente:cliente.salario,
          numeroContaCliente:contaCliente.numeroConta,
          saldoContaCliente:contaCliente.saldo,
          limiteContaCliente:contaCliente.limite,
          cpfGerente:gerenteCliente.cpf,
          nomeGerente:gerenteCliente.nome,
          colorSaldo: contaCliente.saldo >= 0 ? "green" : "red"
        });
      }

      
    });
    
    this.CLIENTS_TABLE.sort((a, b) => a.nomeCliente.localeCompare(b.nomeCliente));

  }
}
