import { Router } from '@angular/router';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { GerenteService } from '../../../core/services/gerente-services';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { GerenteAdmin, Cliente, Conta, PedidoAutoCadastro} from '../../../core/models/entities';
import {  GerenteAutocadastroService} from '../../../core/services/gerente-services/gerente-autocadastro.service';

@Component({
  selector: 'app-gerente-dashboard',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './gerente-dashboard.html',
  styleUrl: './gerente-dashboard.css',
})
export class GerenteDashboard  implements OnInit{
  constructor(
    private router:Router,
    private gerenteService: GerenteService,
    private gerenteAutocadastroService: GerenteAutocadastroService,
  ) {}

  pedidoEmRecusa: PedidoAutoCadastro | null = null;
  motivoRecusaInput = '';

  // Pega o gerente logado atualmente da sessão
  gerenteLogado!: GerenteAdmin;

  ngOnInit(): void {
    const dadosGerente = this.gerenteService.getGerenteLogado();
  
    if(!dadosGerente){
      this.router.navigate(['/login']);
      return;
    }

    this.gerenteLogado = dadosGerente;
  }

  // Dados dos clientes e contas do mock-data
  readonly clientesMock: Cliente[] = CLIENTES_MOCK;
  readonly contasMock: Conta[] = CONTAS_MOCK;

  get nomeGerente(): string {
    return this.gerenteLogado.nome;
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

  get pedidosPendentes(): PedidoAutoCadastro[] {

    const pedidos =  this.gerenteAutocadastroService.getPedidosPendentes(this.gerenteLogado.cpf);

    return pedidos;
  }

  get pedidosProcessados(): PedidoAutoCadastro[] {
    const pedidos = this.gerenteAutocadastroService.getPedidosProcessados(this.gerenteLogado.cpf);
    return pedidos;
  }

  aprovarPedido(cpf: string): void {
    const contaCriada = this.gerenteAutocadastroService.aprovarPedido(cpf, this.gerenteLogado);

    if (!contaCriada) {
      return;
    }

    alert(
      `Cliente aprovado com sucesso. Conta ${contaCriada.numeroConta} criada e senha enviada por e-mail.`,
    );
  }

  iniciarRecusa(pedido: PedidoAutoCadastro): void {
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
      this.pedidoEmRecusa.cpfCliente,
      motivoRecusa,
    );
    if (recusou) {
      alert('Cliente recusado e e-mail com o motivo enviado com sucesso.');
      this.cancelarRecusa();
    }
  }

  atualizarMotivoRecusa(valor: string): void {
    this.motivoRecusaInput = valor;
  }

  // formatarMoeda(valor: number): string {
  //   return new Intl.NumberFormat('pt-BR', {
  //     style: 'currency',
  //     currency: 'BRL',
  //   }).format(valor);
  // }

  formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }
}
