import { DatePipe, DecimalPipe, KeyValuePipe, NgClass } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { booleanAttribute, ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { MovimentacaoCliente } from '../../../core/models/MovimentacaoCliente';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { ResponseModal } from '../../../core/models/response-modal';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';

@Component({
  selector: 'app-extrato',
  imports: [
    FormsModule,
    KeyValuePipe,
    DecimalPipe,
    DatePipe,
    NgClass,
    MatIconModule,
    MatProgressSpinner,
  ],
  templateUrl: './extrato.html',
  styleUrls: ['./extrato.css', '../../shared/css/responseModal.css'],
})
export class Extrato {
  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private clienteService: ClienteService,
    private compositionService: CompositionService,
  ) {}

  mensagem = '';
  dataInicio: Date | null = null;
  dataFim: Date | null = null;
  isLoading: boolean = false;
  extratoAberto:boolean = false;

  clienteConta!: ClienteConta;
  movimentacoes: MovimentacaoCliente[] = [];
  responseModal: ResponseModal | null = null;

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }
  ngOnInit(): void {
    const dadosCarregados = this.clienteService.clienteContaLogado;
    if (dadosCarregados) {
      this.clienteConta = dadosCarregados;
    }
  }

  tirarExtrato() {
    this.changeIsLoading();

    if (!this.dataInicio || !this.dataFim) {
      this.mensagem = 'Por favor, selecione o período de início e fim.';
      return;
    }

    const token = this.authService.usuarioLogado;
    if (!token) return;

    this.compositionService
      .consultarExtrato(
        token,
        this.dataInicio.toString(),
        this.dataFim.toString(),
      )
      .subscribe({
        next: (response) => {
          console.log('Movimentações: ', response.movimentacoes);
          this.movimentacoes = response.movimentacoes;
          this.mensagem = 'Extrato gerado com sucesso!';

          this.extratoAberto = true;
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

  // 3. O agrupador inteligente e calculador de saldo
  get movimentacoesAgrupadasPorDia() {
    const mapa = new Map<
      string,
      { movimentacoes: any[]; saldoConsolidado: number }
    >();

    if (!this.movimentacoes || this.movimentacoes.length === 0) return mapa;

    // Pega o saldo atual
    let saldoAuxiliar = this.clienteConta.conta.saldo;

    let dataAtual = new Date(this.dataFim + 'T00:00:00');
    const dataInicial = new Date(this.dataInicio + 'T00:00:00');

    while (dataAtual >= dataInicial) {
      const key = dataAtual.toLocaleDateString('pt-BR');

      const movsDoDia = this.movimentacoes.filter(
        (m) => new Date(m.dataHora).toLocaleDateString('pt-BR') === key,
      );

      mapa.set(key, {
        movimentacoes: movsDoDia,
        saldoConsolidado: saldoAuxiliar,
      });

      movsDoDia.forEach((m) => {
        if (this.isSaida(m)) {
          saldoAuxiliar += m.valor;
        } else {
          saldoAuxiliar -= m.valor;
        }
      });

      dataAtual.setDate(dataAtual.getDate() - 1);
    }

    return mapa;
  }

  isSaida(mov: any): boolean {
    if (mov.tipoMovimentacao === 'SAQUE') return true;

    const clienteDestinoValido = mov.clienteDestinoId != null && mov.clienteDestinoId != this.clienteConta.cliente.clienteId; 

    if (mov.tipoMovimentacao === 'TRANSFERENCIA' && clienteDestinoValido){
      return true;
    }

    return false;
  }

  getClasseMovimentacao(mov: any): string {
    return this.isSaida(mov) ? 'mov-saida' : 'mov-entrada';
  }
  originalOrder = (a: any, b: any): number => {
    return 0;
  };

  closeModal() {
    this.responseModal = null;
  }

}
