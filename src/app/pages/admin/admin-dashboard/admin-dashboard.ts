import { Component, OnInit } from '@angular/core';
import { InfoCard } from '../../../core/models/info-card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { DecimalPipe } from '@angular/common';
import { ManagerTableData } from '../../../core/models/table-data';
import { Conta, GerenteAdmin } from '../../../core/models/entities';
import { NgxMaskPipe } from 'ngx-mask';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [MatIconModule, DecimalPipe, NgxMaskPipe, MatTableModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {

  constructor(
    private gerentesService: GerenteService,
    private contasService:ContaService,
  ){}

  gerentes: GerenteAdmin[]=[];
  contas: Conta[]=[];
  MANAGERS_TABLE: ManagerTableData[] = []
  infoCards: InfoCard[] = []

  displayedColumns: string[] = [
    'Nome Gerente',
    'CPF',
    'Qtd. Clientes',
    'Saldos Positivos',
    'Saldos Negativos',
    'Saldo Total'];

  ngOnInit(): void {
    this.listarGerentes();
    this.contas=this.contasService.listarTodos();
    this.fillManagersTable();
    this.fillInfoCards();
  }

    listarGerentes():void{
    this.gerentesService.listarTodos().subscribe({
      next: (gerentes: GerenteAdmin[]) => {
      this.gerentes = gerentes;
    },
    error: (erro) => {
      console.log('Erro ao listar gerentes', erro);
      this.gerentes = [];
    }
    })
  }

  fillManagersTable(){

    this.MANAGERS_TABLE=[];
    this.gerentes.forEach((gerente) =>{

      if(gerente.tipo !== "administrador"){

        const contasDoGerente = this.contas.filter(c => c.gerente === gerente.nome);

        const resumo = contasDoGerente.reduce((acc, conta) =>{

          acc.quantidade++;

          if(conta.saldo >= 0){
            acc.positivos += conta.saldo;
          } else{
            acc.negativos += conta.saldo;
          }

          return acc;
        }, {quantidade: 0, positivos: 0, negativos: 0});

        const saldoTotal = resumo.positivos + resumo.negativos;


        this.MANAGERS_TABLE.push({
            nome: gerente.nome,
            cpf:gerente.cpf,
            quantidadeClientes: resumo.quantidade,
            saldosPositivos: resumo.positivos,
            saldosNegativos: resumo.negativos,
            saldoTotal: saldoTotal,
            colorSaldoTotal: saldoTotal >= 0 ? "green" : "red"
          });
        }
    });

    this.MANAGERS_TABLE.sort((a,b) => b.saldosPositivos - a.saldosPositivos);

  }


  get saldosPositivos (){
    let saldo = 0;

    this.contas.forEach((conta) =>{
      if( conta.saldo >= 0){

        saldo += conta.saldo;
      }
    });

    return saldo;
  }

  get saldosNegativos (){
    let saldo = 0;

    this.contas.forEach((conta) =>{
      if(conta.saldo < 0){
        saldo += conta.saldo;
      }
    });

    return saldo;
  }



fillInfoCards(): void {
  this.infoCards = [
    {
      topTitle: 'Total de Clientes',
      icon: 'account_balance_wallet',
      middleContent: this.contas.length.toString(),
      color: 'black',
      bottomText: 'Em todos os gerentes',
    },
    {
      topTitle: 'Saldos Positivos',
      icon: 'trending_up',
      middleContent: this.saldosPositivos.toString(),
      color: 'green',
      bottomText: 'Limite Disponível',
    },
    {
      topTitle: 'Saldos Negativos',
      icon: 'trending_down',
      middleContent: this.saldosNegativos.toString(),
      color: 'red',
      bottomText: 'Saldo + Limite',
    },
  ];
}

}
