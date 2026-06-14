import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { DecimalPipe } from '@angular/common';
import { NgxMaskPipe } from 'ngx-mask';
import { ClientTableData } from '../../../core/models/table-data';
import { CompositionServices } from '../../../core/services/compositon-services/composition-services';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { ClienteConta } from '../../../core/models/ClienteConta';


@Component({
  selector: 'app-admin-relatorio-clientes',
  imports: [MatIconModule, DecimalPipe, NgxMaskPipe, MatTableModule],
  templateUrl: './admin-relatorio-clientes.html',
  styleUrl: './admin-relatorio-clientes.css',
})

export class AdminRelatorioClientes implements OnInit {

  constructor(
    private compositionService: CompositionServices,
    private authService: AuthServices
  ) {}

  clientesContas: ClienteConta[] = [];
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
    this.listarRelatorio();
  }

  listarRelatorio(): void{
    const token = this.authService.usuarioLogado;

    if (!token) {
      console.log('Usuario nao autenticado');
      return;
    }

    this.compositionService.getRelatorioClientes(token).subscribe({
      next: (clientesContas: ClienteConta[]) => {
        this.clientesContas = clientesContas;
        this.fillClientsTable();
      },
      error: (error) =>{
        console.log('Erro ao listar relatorio de clientes', error);
        this.clientesContas = [];

      }
    })};
  

  fillClientsTable() {
    // this.CLIENTS_TABLE = this.clientesContas.map((item) => ({
    //   cpfCliente: item.cliente.cpf,
    //   nomeCliente: item.cliente.nome,
    //   emailCliente: item.cliente.email,
    //   salarioCliente: item.cliente.salario,
    //   numeroContaCliente: item.conta.numeroConta,
    //   saldoContaCliente: item.conta.saldo,
    //   limiteContaCliente: item.conta.limite,
    //   cpfGerente: item.conta.cpfGerente,
    //   nomeGerente: item.conta.gerente.nomeGerente,
    //   colorSaldo: item.conta.saldo >= 0 ? 'green' : 'red'
    // }));

    // this.CLIENTS_TABLE.sort((a, b) => a.nomeCliente.localeCompare(b.nomeCliente));
  }
}


