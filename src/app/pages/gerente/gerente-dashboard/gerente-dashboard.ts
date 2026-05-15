import { Router } from '@angular/router';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ResponseModal } from '../../../core/models/response-modal';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';
import { GerenteAdmin, Cliente, Conta, PedidoAutoCadastro} from '../../../core/models/entities';
//import {  GerenteAutocadastroService} from '../../../core/services/gerente-services/gerente-autocadastro.service';
import { MatIconModule } from '@angular/material/icon';
import { GerenteAutocadastroService } from '../../../core/services/gerente-services/gerente-autocadastro.service';

@Component({
  selector: 'app-gerente-dashboard',
  imports: [DatePipe, DecimalPipe, MatIconModule],
  templateUrl: './gerente-dashboard.html',
  styleUrls:[ './gerente-dashboard.css', '../../shared/css/responseModal.css'],
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
  responseModal: ResponseModal | null = null;

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

    this.responseModal = {
      title: "Cliente Aprovado com sucesso!",
      message: `Conta ${contaCriada.numeroConta} criada e senha enviada por e-mail.`,
      messageIcon: "check",
      type: 'success'
    };

  }

  closeModal(){
    this.responseModal = null;
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

      this.responseModal = {
        title: "Campo incompleto",
        message: "Preencha o motivo da recusa",
        messageIcon: "error",
        type: 'error'
      };

      return;
    }

    const recusou = this.gerenteAutocadastroService.recusarPedido(
      this.pedidoEmRecusa.cpfCliente,
      motivoRecusa,
    );
    if (recusou) {

      this.responseModal = {
        title: "Cliente Recusado",
        message: "E-mail com motivo enviado com sucesso!",
        messageIcon: "check",
        type: 'success'
      };

      this.cancelarRecusa();
    }
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
