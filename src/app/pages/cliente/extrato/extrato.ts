import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  ClienteOutdated,
  ContaOutdated,
  Movimentacao,
} from '../../../core/models/entities';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { Router } from '@angular/router';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';
import { DatePipe, DecimalPipe, KeyValuePipe, NgClass } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { ClienteConta } from '../../../core/models/ClienteConta';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';

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
    private clienteService: ClienteService,
    private movimentacaoService: MovimentacaoService,
    private cdr: ChangeDetectorRef,
  ) {}

  mensagem = '';
  dataInicio: Date | null = null;
  dataFim: Date | null = null;

  clienteConta!: ClienteConta;
  movimentacoes: Movimentacao[] = [];

  ngOnInit(): void {
    // const dadosCarregados = this.clienteService.clienteContaLogado;

    // if (dadosCarregados) {
    //   this.clienteConta = dadosCarregados;
    //   this.movimentacaoService
    //     .buscarMovimentacoesPorCPFCliente(this.clienteConta.cpf)
    //     .subscribe({
    //       next: (listaMovimentacoes: Movimentacao[]) => {
    //         this.movimentacoes = listaMovimentacoes;
    //         console.log(
    //           `Movimentações do ${this.clienteConta.nome}: `,
    //           this.movimentacoes,
    //         );
    //         this.cdr.detectChanges();
    //       },
    //       error: (err) => {
    //         console.error('Erro ao carregar extrato do servidor', err);
    //       },
    //     });
    // } else {
    //   console.log('Nenhum dado encontrado no localStorage para o Dashboard.');
    // }
  }
  // Lógica inteligente de retrocesso de saldo para montar o extrato consolidado por dia
  get movimentacoesAgrupadasPorDia(): Map<
    string,
    { movimentacoes: Movimentacao[]; saldoConsolidado: number }
  > {
    const mapa = new Map<
      string,
      { movimentacoes: Movimentacao[]; saldoConsolidado: number }
    >();

    if (!this.dataInicio || !this.dataFim || this.movimentacoes.length === 0) {
      return mapa;
    }

    // Filtra e ordena as movimentações pela data (da mais nova para a mais antiga)
    const todasMovs: Movimentacao[] = [];

    // this.movimentacoes
    //   .filter(m => m.cpfClienteOrigem === this.clienteConta.cpf || m.cpfClienteDestino === this.clienteConta.cpf)
    //   .sort((a, b) => new Date(b.data_hora).getTime() - new Date(a.data_hora).getTime());

    // Pega o saldo atual armazenado na conta do localStorage
    let saldoAuxiliar = this.clienteConta.conta.saldo;

    let dataAtual = new Date(this.dataFim + 'T00:00:00');
    const dataInicial = new Date(this.dataInicio + 'T00:00:00');

    while (dataAtual >= dataInicial) {
      const key = dataAtual.toLocaleDateString('pt-BR');

      // Filtra as movimentações ocorridas exatamente neste dia
      const movsDoDia = todasMovs.filter(
        (m) => new Date(m.data_hora).toLocaleDateString('pt-BR') === key,
      );

      mapa.set(key, {
        movimentacoes: movsDoDia,
        saldoConsolidado: saldoAuxiliar,
      });

      // Lógica reversa: Reconstrói o saldo retrocedendo os passos das transações
      movsDoDia.forEach((m) => {
        // const isEntrada =
        //   m.tipo === 'deposito' ||
        //   (m.tipo === 'transferencia' &&
        //     m.cpfClienteDestino === this.clienteConta.cpf);

        // if (isEntrada) {
        //   saldoAuxiliar -= m.valor; // Se entrou dinheiro hoje, no dia anterior o saldo era menor
        // } else {
        //   saldoAuxiliar += m.valor; // Se saiu dinheiro hoje, no dia anterior o saldo era maior
        // }
      });

      dataAtual.setDate(dataAtual.getDate() - 1);
    }

    return mapa;
  }

  get movimentacoesPorData(): Movimentacao[] {
    if (!this.dataInicio || !this.dataFim) return [];

    const inicio = new Date(this.dataInicio + 'T00:00:00');
    const fim = new Date(this.dataFim + 'T23:59:59');

    return this.movimentacoes.filter((mov) => {
      const dataMov = new Date(mov.data_hora);
      return dataMov >= inicio && dataMov <= fim;
    });
  }

  isSaida(mov: Movimentacao): boolean {
    // // Adaptado para checar contra o CPF correto da sua interface mapeada
    // return (
    //   mov.tipo === 'saque' ||
    //   (mov.tipo === 'transferencia' &&
    //     mov.cpfClienteOrigem === this.clienteConta.cpf)
    // );

    return false;
  }

  getClasseMovimentacao(mov: Movimentacao): string {
    return this.isSaida(mov) ? 'mov-saida' : 'mov-entrada';
  }

  originalOrder = (a: any, b: any): number => {
    return 0;
  };

  tirarExtrato() {
    if (!this.dataInicio || !this.dataFim) {
      this.mensagem = 'Por favor, selecione o período de início e fim.';
      return;
    }

    this.mensagem = 'Extrato realizado com sucesso';
    this.cdr.detectChanges(); // Força a atualização do DOM exibindo o mapa gerado
  }

  // get movimentacoesAgrupadasPorDia(): Map<string, { movimentacoes: Movimentacao[], saldoConsolidado: number }> {
  //   const mapa = new Map<string, { movimentacoes: Movimentacao[], saldoConsolidado: number }>();

  // //movimentações ORDENADAS pela data (mais nova para mais antiga)
  // const todasMovs = this.movimentacaoService.listarTodos()
  //   .filter(m => m.cpfClienteOrigem === this.cliente.cpf || m.cpfClienteDestino === this.cliente.cpf)
  //   .sort((a, b) => new Date(b.data_hora).getTime() - new Date(a.data_hora).getTime());

  // // saldo que o cliente tem AGORA
  // let saldoAuxiliar = this.contaCliente.saldo;

  // //range de datas (do fim para o início)
  // let dataAtual = new Date(this.dataFim! + 'T00:00:00');
  // const dataInicial = new Date(this.dataInicio!+ 'T00:00:00' );

  // while (dataAtual >= dataInicial) {
  //   const key = dataAtual.toLocaleDateString('pt-BR');

  //   // movimentações EXATAMENTE deste dia
  //   const movsDoDia = todasMovs.filter(m =>
  //     new Date(m.data_hora).toLocaleDateString('pt-BR') === key
  //   );

  //   //saldoAuxiliar antes de "descontar" as movs do dia anterior
  //   mapa.set(key, {
  //     movimentacoes: movsDoDia,
  //     saldoConsolidado: saldoAuxiliar
  //   });

  //   //"desfazer" as transações deste dia
  //   movsDoDia.forEach(m => {
  //     const isEntrada = m.tipo === 'deposito' || (m.tipo === 'transferencia' && m.cpfClienteDestino === this.cliente.cpf);

  //     if (isEntrada) {
  //       saldoAuxiliar -= m.valor; // Se entrou dinheiro hoje, no dia anterior o saldo era menor
  //     } else {
  //       saldoAuxiliar += m.valor; // Se saiu dinheiro hoje, no dia anterior o saldo era maior
  //     }
  //   });

  //   dataAtual.setDate(dataAtual.getDate() - 1);
  // }

  //   return mapa;
  // }

  // get movimentacoesPorData(): Movimentacao[] {

  //   const inicio = new Date(this.dataInicio + 'T00:00:00');
  //   const fim = new Date(this.dataFim + 'T23:59:59');

  //   return this.movimentacoes.filter((mov) => {
  //     const dataMov = new Date(mov.data_hora);
  //     return dataMov >= inicio && dataMov <= fim;
  //   });
  // }

  //     // Define se a cor deve ser azul ou vermelha
  //   isSaida(mov: Movimentacao): boolean {
  //     return mov.tipo === 'saque' || (mov.tipo === 'transferencia' && mov.cpfClienteOrigem === this.cliente.cpf);
  //   }

  //   getClasseMovimentacao(mov: Movimentacao): string {
  //     return this.isSaida(mov) ? 'mov-saida' : 'mov-entrada';
  //   }

  //   // Função para o keyvalue manter a ordem que você definiu no Map
  //   originalOrder = (a: any, b: any): number => {
  //     return 0;
  //   }

  // cliente!: Cliente;
  // contaCliente!: Conta;
  // movimentacoes!: Movimentacao[];

  // tirarExtrato() {
  //   this.mensagem="Extrato realizado com sucesso"
  //   return
  // }
}
