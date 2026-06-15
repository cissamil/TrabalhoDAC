import { DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { NgxMaskPipe } from 'ngx-mask';
import { ClienteRelatorio } from '../../../core/models/RelatorioClientes';
import { ResponseModal } from '../../../core/models/response-modal';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';

@Component({
  selector: 'app-admin-relatorio-clientes',
  imports: [
    MatIconModule,
    DecimalPipe,
    NgxMaskPipe,
    MatTableModule,
    MatProgressSpinner,
  ],
  templateUrl: './admin-relatorio-clientes.html',
  styleUrls: [
    './admin-relatorio-clientes.css',
    '../../shared/css/responseModal.css',
  ],
})
export class AdminRelatorioClientes implements OnInit {
  constructor(
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private compositionService: CompositionService,
  ) {}

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

  clientes: ClienteRelatorio[] = [];

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

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

  listarRelatorio(): void {
    const token = this.authService.usuarioLogado;

    if (!token) {
      console.log('Usuario nao autenticado');
      return;
    }

    this.changeIsLoading();

    this.compositionService.getRelatorioClientes(token).subscribe({
      next: (relatorio: ClienteRelatorio[]) => {
        this.clientes = relatorio.sort((a, b) => a.cliente.nome.localeCompare(b.cliente.nome));

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

  closeModal() {
    this.responseModal = null;
  }
}
