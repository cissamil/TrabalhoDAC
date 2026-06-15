import { DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { NgxMaskPipe } from 'ngx-mask';
import { GerenteResumo } from '../../../core/models/GerenteResumo';
import { InfoCard } from '../../../core/models/info-card';
import { ResponseModal } from '../../../core/models/response-modal';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    MatIconModule,
    DecimalPipe,
    NgxMaskPipe,
    MatTableModule,
    MatProgressSpinner,
  ],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css', '../../shared/css/responseModal.css'],
})
export class AdminDashboard implements OnInit {
  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }
  constructor(
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private compositionService: CompositionService,
  ) {}

  infoCards: InfoCard[] = [];

  gerentes: GerenteResumo[] = [];

  displayedColumns: string[] = [
    'Nome Gerente',
    'CPF',
    'Qtd. Clientes',
    'Saldos Positivos',
    'Saldos Negativos',
  ];

  ngOnInit(): void {
    this.carregarDashboard();
  }

  carregarDashboard(): void {
    this.changeIsLoading();
    const token = this.authService.usuarioLogado;
    if (!token) {
      return;
    }

    this.compositionService.getDashboardAdmin(token).subscribe({
      next: (resumoGerentes) => {
        this.gerentes = resumoGerentes;

        this.fillInfoCards();

        this.changeIsLoading();

      },
      error: (erro: HttpErrorResponse) => {
        console.error('Erro Interceptado: ', erro);

        const backendError = erro.error as StandartErrorResponse;

        this.responseModal = {
          title: backendError?.error || 'Erro ao processar requisição',
          message:
            backendError?.message ||
            'Ocorreu um erro ao processar sua requisição. Tente novamente',
          messageIcon: 'error',
          type: 'error',
        };

        this.changeIsLoading();
      },
    });
  }

  get TotalClientes(): number {
    return this.gerentes
      .filter((g) => (g.quantidadeClientes ?? 0) >= 0)
      .reduce((soma, g) => soma + (g.quantidadeClientes ?? 0), 0);
  }
  get totalSaldosPositivos(): number {
    return this.gerentes
      .filter((g) => (g.somaSaldosPositivos ?? 0) >= 0)
      .reduce((soma, g) => soma + (g.somaSaldosPositivos ?? 0), 0);
  }

  get totalSaldosNegativos(): number {
    return this.gerentes
      .filter((g) => (g.somaSaldosNegativos ?? 0) < 0)
      .reduce((soma, g) => soma + (g.somaSaldosNegativos ?? 0), 0);
  }

  closeModal() {
    this.responseModal = null;
  }

  fillInfoCards(): void {
    this.infoCards = [
      {
        topTitle: 'Total de Clientes',
        icon: 'account_balance_wallet',
        middleContent: this.TotalClientes.toString(),
        color: 'black',
        bottomText: 'Em todos os gerentes',
      },
      {
        topTitle: 'Saldos Positivos',
        icon: 'trending_up',
        middleContent: this.totalSaldosPositivos.toString(),
        color: 'green',
        bottomText: 'Limite Disponível',
      },
      {
        topTitle: 'Saldos Negativos',
        icon: 'trending_down',
        middleContent: this.totalSaldosNegativos.toString(),
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
