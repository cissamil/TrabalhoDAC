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
    this.clientes = this.clienteService.listarTodos();
    this.contas = this.contaService.listarTodos();
    this.gerentes = this.gerenteService.listarGerentes();
    this.fillClientsTable();
  }

  fillClientsTable() {
    this.CLIENTS_TABLE=[];
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
