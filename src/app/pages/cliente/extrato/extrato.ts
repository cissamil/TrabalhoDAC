import { DatePipe, DecimalPipe, KeyValuePipe, NgClass } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { MovimentacaoCliente } from '../../../core/models/MovimentacaoCliente';
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
  ],
  templateUrl: './extrato.html',
  styleUrl: './extrato.css',
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

  clienteConta!: ClienteConta;
  movimentacoes: MovimentacaoCliente[] = [];

  ngOnInit(): void {
    const dadosCarregados = this.clienteService.clienteContaLogado;
    if (dadosCarregados) {
      this.clienteConta = dadosCarregados;
    }
  }

  tirarExtrato() {
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

          console.log("Movimentações: ", response.movimentacoes);
          this.movimentacoes = response.movimentacoes;
          this.mensagem = 'Extrato gerado com sucesso!';
          this.cdr.detectChanges(); 
        },
        error: (erro) => {
          console.error('Erro ao buscar extrato: ', erro);
          this.mensagem = 'Erro ao buscar extrato. Tente novamente.';
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
    if (mov.tipo_movimentacao === 'SAQUE') return true;
    
    if (
      mov.tipo_movimentacao === 'TRANSFERENCIA' &&
      mov.cliente_destino_nome != null
    )
      return true;

    return false; 
  }

  getClasseMovimentacao(mov: any): string {
    return this.isSaida(mov) ? 'mov-saida' : 'mov-entrada';
  }
  originalOrder = (a: any, b: any): number => {
    return 0;
  };
}
