import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ClienteGerente } from '../../../core/models/ClienteGerente';
import { ResponseModal } from '../../../core/models/response-modal';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { MatIcon } from "@angular/material/icon";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

interface ClienteTabela {
  cpf: string;
  nome: string;
  cidade: string;
  estado: string;
  email: string;
  salario: number;
  saldo: number;
  limite: number;
}

@Component({
  selector: 'app-todos-clientes',
  imports: [FormsModule, RouterLink, NgxMaskDirective, MatIcon, MatProgressSpinner],
  templateUrl: './todos-clientes.html',
  styleUrls: ['./todos-clientes.css', '../../shared/css/responseModal.css'],
})
export class TodosClientes implements OnInit{
  
  termoBusca = '';
  clientes: ClienteGerente[] = [];
  responseModal: ResponseModal | null = null;
  
  isLoading:boolean = false;

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

  closeModal() {
    this.responseModal = null;
  }

  
  constructor(
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private gerenteService: GerenteService,
    private compositionService: CompositionService
  ) {}

  ngOnInit(): void{
    this.listarClientes();
  }

  listarClientes(): void {
    
    const usuarioLogado = this.authService.usuarioLogado;

    if(!usuarioLogado) return;

    this.changeIsLoading();

    this.compositionService.getClientesDoGerente(usuarioLogado).subscribe({
      
      next: (clientesDoGerente) => {

        this.clientes = clientesDoGerente;


        this.changeIsLoading();


      }, error: (erro: HttpErrorResponse) => {
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


  get clientesFiltrados(): ClienteGerente[] {
    const termo = this.termoBusca.trim().toLowerCase();
    if (!termo) {
      return this.clientes;
    }

    const termoCpf = termo.replace(/\D/g, '');

    return this.clientes.filter(
      (cliente) => {
        const cpfNumerico = cliente.cpf.replace(/\D/g, '');
        const nomeNormalizado = cliente.nome.toLowerCase();

        const correspondeCpf = termoCpf.length > 0 && cpfNumerico.startsWith(termoCpf);
        const correspondeNome = nomeNormalizado.startsWith(termo);

        return correspondeCpf || correspondeNome;
      }
    );
  }

  get isBuscaCpf(): boolean {
    return this.comecaComNumero(this.termoBusca);
  }

  onTermoBuscaChange(valor: string): void {
    if (!valor) {
      this.termoBusca = '';
      return;
    }

    if (this.comecaComNumero(valor)) {
      this.termoBusca = valor.slice(0, 14);
      return;
    }

    this.termoBusca = valor.slice(0, 50);
  }

  private extrairCidadeEstado(endereco: string): { cidade: string; estado: string } {
    if (!endereco || !endereco.includes(' - ')) {
      return { cidade: 'Não Informada', estado: '-' };
    }
    const partes = endereco.split(' - ').map((item) => item.trim());
    return {
      cidade: partes.at(-2) ?? '-',
      estado: partes.at(-1) ?? '-',
    };
  }

  formatarCpf(cpf: string): string {
    if (!cpf) return '';
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }

  private comecaComNumero(valor: string): boolean {
    const texto = valor.trimStart();
    if (!texto) {
      return false;
    }

    return /^\d/.test(texto);
  }
}
