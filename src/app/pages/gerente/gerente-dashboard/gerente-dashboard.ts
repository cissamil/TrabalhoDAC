import { Component, inject } from '@angular/core';
import {
  GerenteAutocadastroService,
  PedidoAutocadastro,
} from '../../../core/services/gerente-services/gerente-autocadastro.service';
import { GerenteService } from '../../../core/services/gerente-services';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { GerenteAdmin, Cliente, Conta } from '../../../core/models/entities';

@Component({
  selector: 'app-gerente-dashboard',
  imports: [],
  templateUrl: './gerente-dashboard.html',
  styleUrl: './gerente-dashboard.css',
})
export class GerenteDashboard {
  private readonly gerenteAutocadastroService = inject(GerenteAutocadastroService);
  private readonly gerenteService = inject(GerenteService);
  pedidoEmRecusa: PedidoAutocadastro | null = null;
  motivoRecusaInput = '';

  // Pega o gerente logado atualmente da sessão
  readonly gerenteLogado: GerenteAdmin | null =
    this.gerenteService.getGerenteLogado();

  // Dados dos clientes e contas do mock-data
  readonly clientesMock: Cliente[] = CLIENTES_MOCK;
  readonly contasMock: Conta[] = CONTAS_MOCK;

  get nomeGerente(): string {
    return this.gerenteLogado?.nome ?? 'Gerente';
  }

  get totalClientes(): number {
    return this.clientesMock.length;
  }

  get saldoTotalContas(): number {
    return this.contasMock.reduce((total, conta) => total + conta.saldo, 0);
  }

  get limiteTotalDisponivel(): number {
    return this.contasMock.reduce((total, conta) => total + conta.limite, 0);
  }

  get pedidosPendentes(): PedidoAutocadastro[] {
    return this.gerenteAutocadastroService.getPedidosPendentes();
  }

  get pedidosProcessados(): PedidoAutocadastro[] {
    return this.gerenteAutocadastroService.getPedidosProcessados();
  }

  aprovarPedido(cpf: string): void {
    const contaCriada = this.gerenteAutocadastroService.aprovarPedido(cpf, this.nomeGerente);
    if (!contaCriada) {
      return;
    }

    alert(
      `Cliente aprovado com sucesso. Conta ${contaCriada.numeroConta} criada e senha enviada por e-mail.`
    );
  }

  iniciarRecusa(pedido: PedidoAutocadastro): void {
    this.pedidoEmRecusa = pedido;
    this.motivoRecusaInput = '';
  }

  cancelarRecusa(): void {
    this.pedidoEmRecusa = null;
    this.motivoRecusaInput = '';
  }

  confirmarRecusa(): void {
    if (!this.pedidoEmRecusa) {
      return;
    }

    const motivoRecusa = this.motivoRecusaInput.trim();
    if (!motivoRecusa) {
      alert('A recusa exige um motivo.');
      return;
    }

    const recusou = this.gerenteAutocadastroService.recusarPedido(
      this.pedidoEmRecusa.cpf,
      motivoRecusa
    );
    if (recusou) {
      alert('Cliente recusado e e-mail com o motivo enviado com sucesso.');
      this.cancelarRecusa();
    }
  }

  atualizarMotivoRecusa(valor: string): void {
    this.motivoRecusaInput = valor;
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }

  formatarData(data?: Date): string {
    if (!data) {
      return '-';
    }

    return new Intl.DateTimeFormat('pt-BR', {
      dateStyle: 'short',
      timeStyle: 'medium',
    }).format(data);
  }

  formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }
}
