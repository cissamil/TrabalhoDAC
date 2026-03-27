import { Component, inject } from '@angular/core';
import {
  EmailNotificacao,
  GerenteAutocadastroService,
  PedidoAutocadastro,
} from '../../../core/services/gerente-autocadastro.service';
import { STAFF_MOCK } from '../../../core/mock/mock-data';
import { GerenteAdmin } from '../../../core/models/entities';

@Component({
  selector: 'app-gerente-dashboard',
  imports: [],
  templateUrl: './gerente-dashboard.html',
  styleUrl: './gerente-dashboard.css',
})
export class GerenteDashboard {
  private readonly gerenteAutocadastroService = inject(GerenteAutocadastroService);

  // Simula o usuario logado: atualmente pega o primeiro usuario do tipo gerente do mock.
  readonly gerenteLogado: GerenteAdmin | null =
    STAFF_MOCK.find((usuario) => usuario.tipo === 'gerente') ?? null;

  get nomeGerente(): string {
    return this.gerenteLogado?.nome ?? 'Gerente';
  }

  get pedidosPendentes(): PedidoAutocadastro[] {
    return this.gerenteAutocadastroService.getPedidosPendentes();
  }

  get pedidosProcessados(): PedidoAutocadastro[] {
    return this.gerenteAutocadastroService.getPedidosProcessados();
  }

  get emailsEnviados(): EmailNotificacao[] {
    return this.gerenteAutocadastroService.getEmailsEnviados();
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

  recusarPedido(cpf: string): void {
    const motivoRecusa = prompt('Informe o motivo da recusa:')?.trim();
    if (!motivoRecusa) {
      alert('A recusa exige um motivo.');
      return;
    }

    const recusou = this.gerenteAutocadastroService.recusarPedido(cpf, motivoRecusa);
    if (recusou) {
      alert('Cliente recusado e e-mail com o motivo enviado com sucesso.');
    }
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
}
