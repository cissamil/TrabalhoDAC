import { CurrencyPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';
import { ContaAprovar } from '../../../core/models/ContaAprovar';
import { ContaPendente, GerenteContasPendentes } from '../../../core/models/ContaGerente';
import { ContaRejeitar } from '../../../core/models/ContaRejeitar';
import { ResponseModal } from '../../../core/models/response-modal';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';

@Component({
  selector: 'app-gerente-dashboard',
  imports: [
    CurrencyPipe,
    MatIconModule,
    MatProgressSpinner,
  ],
  templateUrl: './gerente-dashboard.html',
  styleUrls: ['./gerente-dashboard.css', '../../shared/css/responseModal.css'],
})
export class GerenteDashboard implements OnInit {
  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private contaService: ContaService,
    private compositionService: CompositionService,
  ) {}

  gerenteContasPendentes!: GerenteContasPendentes;
  nomeGerente: string = '';
  contasPendentes: ContaPendente[] = [];
  contaEmRecusa: ContaPendente | null = null;
  motivoRecusaInput = '';
  contasGerente: GerenteContasPendentes[] = [];

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

  getUpdatedGerenteData() {
    const token = this.authService.usuarioLogado;
    if (!token) {
      return;
    }

    this.compositionService.getContasPendentes(token).subscribe({
      next: (gerenteContasPendentes) => {
        this.gerenteContasPendentes = gerenteContasPendentes;

        this.fillGerenteContasPendentes(gerenteContasPendentes);
      },
    });
  }

  fillGerenteContasPendentes(dadosCarregados: GerenteContasPendentes) {
    this.gerenteContasPendentes = dadosCarregados;
    this.incializarDashboard();
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.getUpdatedGerenteData();

    this.isLoading = false;
  }

  incializarDashboard() {
    this.nomeGerente = this.gerenteContasPendentes.gerente.nome;
    this.contasPendentes = this.gerenteContasPendentes.contas;

    this.cdr.detectChanges();
  }

  aprovarPedido(conta: ContaPendente): void {
    this.changeIsLoading();

    const token = this.authService.usuarioLogado;
    if (!token) {
      return;
    }

    const clienteSalario: ContaAprovar = {
      clienteSalario: conta.clienteSalario,
    };

    this.contaService
      .aprovarConta(conta.contaId, clienteSalario, token)
      .subscribe({
        next: () => {
          setTimeout(() => {
            this.getUpdatedGerenteData();

            this.responseModal = {
              title: 'Conta Ativada!',
              message:
                'O status mudou para APROVADA e as credenciais foram geradas.',
              messageIcon: 'check',
              type: 'success',
            };

            this.changeIsLoading();
          }, 500);
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

  iniciarRecusa(pedido: ContaPendente): void {
    this.contaEmRecusa = pedido;
    this.motivoRecusaInput = '';
  }

  cancelarRecusa(): void {
    this.contaEmRecusa = null;
    this.motivoRecusaInput = '';
  }

  confirmarRecusa(): void {
    if (!this.contaEmRecusa) return;

    const token = this.authService.usuarioLogado;
    if (!token) {
      return;
    }
    
    const motivoRecusa = this.motivoRecusaInput.trim();
    
    if (!motivoRecusa) {
      this.responseModal = {
        title: 'Campo incompleto',
        message: 'Preencha o motivo da recusa',
        messageIcon: 'error',
        type: 'error',
      };
      
      return;
    }
    
    this.changeIsLoading();

    const contaRecusar: ContaRejeitar = {
      motivoRecusa: motivoRecusa,
    };

    this.contaService
      .rejeitarConta(this.contaEmRecusa.contaId, contaRecusar, token)
      .subscribe({
        next: () => {
          setTimeout(() => {
            this.getUpdatedGerenteData();

            this.responseModal = {
              title: 'Cliente Recusado',
              message: 'E-mail com motivo enviado com sucesso!',
              messageIcon: 'check',
              type: 'success',
            };

            this.contaEmRecusa = null;

            this.changeIsLoading();
          }, 500);
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

  atualizarMotivoRecusa(valor: string): void {
    this.motivoRecusaInput = valor;
  }

  formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }
}
