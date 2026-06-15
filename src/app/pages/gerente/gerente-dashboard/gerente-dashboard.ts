import { Router } from '@angular/router';
import { DatePipe, DecimalPipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ResponseModal } from '../../../core/models/response-modal';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { GerenteAdmin } from '../../../core/models/entities';
import { MatIconModule } from '@angular/material/icon';
import { GerenteAutocadastroService } from '../../../core/services/gerente-services/gerente-autocadastro.service';
import { ContaCliente, GerenteContasPendentes } from '../../../core/models/ContaGerente';
import { AuthService } from '../../../core/services/auth-services/auth-services';

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
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {}

  tokenJWT='';

  pedidosPendentes: GerenteContasPendentes[]=[];
  pedidosProcessados: GerenteContasPendentes[]=[];
  pedidoEmRecusa: GerenteContasPendentes | null = null;
  motivoRecusaInput = '';
  contasGerente: GerenteContasPendentes[]=[];

  // Pega o gerente logado atualmente da sessão
  gerenteLogado!: GerenteAdmin;
  responseModal: ResponseModal | null = null;

  ngOnInit(): void {
    this.tokenJWT=this.authService.usuarioLogado || '';
    const dadosGerente = this.gerenteService.GerenteLogado;

    if(dadosGerente){
      this.gerenteLogado=dadosGerente;
      this.contasGerente=dadosGerente.contas || [];

      this.pedidosPendentes=this.contasGerente.filter(
      (conta: GerenteContasPendentes)=> !conta.contaId
    );

    this.pedidosProcessados=(dadosGerente.contas || []).filter(
      (conta: GerenteContasPendentes)=> conta.contaId !== null
    );
    this.cdr.detectChanges();
    }else{
      this.router.navigate(['/login']);
    }
  }

//---------geters e setters de gerente -----------
  get nomeGerente(): string {
    return this.gerenteLogado?.nome || '';
  }


  aprovarPedido(idCliente: string): void {
    this.gerenteAutocadastroService.aprovarConta(idCliente, this.tokenJWT).subscribe({
      next: ()=>{
        this.responseModal={
          title: "Conta Ativada!",
          message: "O status mudou para APROVADA e as credenciais foram geradas.",
          messageIcon: "check",
          type: 'success'
        };
        //remove visualmente de pendentes
        this.pedidosPendentes=this.pedidosPendentes.filter(p=>p.clienteId !== idCliente);
        const contaTratada=this.contasGerente.find(c=> c.clienteId === idCliente);
        if(contaTratada){
          contaTratada.statusConta='CONTA_APROVADA' as any;
          this.pedidosProcessados=[...this.pedidosProcessados, contaTratada];
        }
      },
      error: (err)=>console.error("Erro ao aprovar conta:", err)

    });

  }

  closeModal(){
    this.responseModal = null;
  }


  iniciarRecusa(pedido: GerenteContasPendentes): void {
    this.pedidoEmRecusa = pedido;
    this.motivoRecusaInput = '';
  }

  cancelarRecusa(): void {
    this.pedidoEmRecusa = null;
    this.motivoRecusaInput = '';
  }

  confirmarRecusa():void{
    if (!this.pedidoEmRecusa || !this.motivoRecusaInput.trim()) return;
    const idCliente = this.pedidoEmRecusa.clienteId;
    this.gerenteAutocadastroService.recusarConta(idCliente, this.motivoRecusaInput, this.tokenJWT).subscribe({
      next: () => {
      this.responseModal = {
        title: "Cadastro Recusado",
        message: "O status da conta foi alterado para REJEITADA.",
        messageIcon: "check",
        type: 'success'
      };
      this.pedidosPendentes= this.pedidosPendentes.filter(p => p.clienteId !== idCliente);
      const contaTratada=this.contasGerente.find(c=> c.clienteId === idCliente);
        if(contaTratada){
          contaTratada.statusConta= 'CONTA_REJEITADA' as any;
          this.pedidosProcessados=[...this.pedidosProcessados, contaTratada];
        }
      this.cancelarRecusa();
    },
    error: (err) => console.error("Erro ao recusar conta:", err)
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
