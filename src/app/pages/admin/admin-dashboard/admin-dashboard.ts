import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { InfoCard } from '../../../core/models/info-card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { DecimalPipe } from '@angular/common';
import { ManagerTableData } from '../../../core/models/table-data';
import { NgxMaskPipe } from 'ngx-mask';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { ContaGerente } from '../../../core/models/ContaGerente';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-admin-dashboard',
  imports: [MatIconModule, DecimalPipe, NgxMaskPipe, MatTableModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit, OnDestroy {
  private readonly subscriptions = new Subscription();

  constructor(
    private contasService: ContaService,
    private cdr: ChangeDetectorRef,
    private authService: AuthServices
  ){}

  tokenJWT='';
  contas: ContaGerente[]=[];
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
    this.tokenJWT=this.authService.usuarioLogado || '';
    this.carregarDashboard();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

carregarDashboard(): void {
  this.subscriptions.add(
      this.contasService.listarTodos(this.tokenJWT).subscribe({
        next: (listaContas: ContaGerente[]) => {
          this.contas = listaContas || [];
this.fillManagersTable();
          this.fillInfoCards();
          this.cdr.detectChanges();
        },
        error: (erro) => {
          console.error('Erro ao buscar dados agregados de contas/gerentes:', erro);
          this.contas = [];
        }
      })
    );
  }


  fillManagersTable(): void{

    this.MANAGERS_TABLE=[];
    const gerentesMap = new Map<string, {nome: string; cpf:string}>();
    this.contas.forEach(conta => {
      // if (conta.cpfGerente && !gerentesMap.has(conta.cpfGerente)) {
      //   gerentesMap.set(conta.cpfGerente, {
      //     nome: conta.gerente?.nomeGerente || conta.cliente || 'Gerente Operacional',
      //     cpf: conta.cpfGerente
      //   });
      // }
    });

    gerentesMap.forEach((dadosGerente)=>{
      // const contasGerente = this.contas.filter(c=> c.cpfGerente === dadosGerente.cpf);
      // const resumo= contasGerente.reduce((acc, conta)=>{
      //   acc.quantidade++;

      //   const saldoAtual = conta.saldo ?? 0;
      //   if(saldoAtual >=0){
      //     acc.positivos+=saldoAtual;
      //   }else{
      //     acc.negativos+= saldoAtual;
      //   }
      //   return acc;
      // },{quantidade: 0, positivos: 0, negativos: 0});
      // const saldoTotal = resumo.positivos + resumo.negativos;

      // this.MANAGERS_TABLE.push({
      //       nome: dadosGerente.nome,
      //       cpf:dadosGerente.cpf,
      //       quantidadeClientes: resumo.quantidade,
      //       saldosPositivos: resumo.positivos,
      //       saldosNegativos: resumo.negativos,
      //       saldoTotal: saldoTotal,
      //       colorSaldoTotal: saldoTotal >= 0 ? "green" : "red"
      //     });
    })

    this.MANAGERS_TABLE.sort((a,b) => b.saldosPositivos - a.saldosPositivos);
  }


  get totalSaldosPositivos(): number{
    return this.contas
    .filter(c=>(c.saldo ?? 0)>=0)
    .reduce((acc,c)=> acc +(c.saldo ?? 0), 0);
  }


  get totalSaldosNegativos(): number{
    return this.contas
    .filter(c=>(c.saldo ?? 0)<0)
    .reduce((acc,c)=> acc+(c.saldo ?? 0), 0);
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
      middleContent: this.formatarMoeda(this.totalSaldosPositivos),
      color: 'green',
      bottomText: 'Limite Disponível',
    },
    {
      topTitle: 'Saldos Negativos',
      icon: 'trending_down',
      middleContent: this.formatarMoeda(this.totalSaldosNegativos),
      color: 'red',
      bottomText: 'Saldo + Limite',
    },
  ];
}
formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor || 0);
  }
}
