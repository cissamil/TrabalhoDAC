//ok
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { NgxMaskPipe, NgxMaskDirective } from 'ngx-mask';
import { ClienteGerente } from '../../../core/models/ClienteGerente';
import { ResponseModal } from '../../../core/models/response-modal';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ContaService } from '../../../core/services/conta-services/conta-service';

@Component({
  selector: 'app-cliente-especifico',
  imports: [FormsModule, NgxMaskPipe, MatIcon, NgxMaskDirective],
  templateUrl: './cliente-especifico.html',
  styleUrls: ['./cliente-especifico.css', '../../shared/css/responseModal.css'],
})
export class ClienteEspecifico implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private compositionService: CompositionService,
    private cdr: ChangeDetectorRef,
  ) {}

  termoBusca = '';
  clientes: ClienteGerente[] = [];
  clienteSelecionado: ClienteGerente | null = null;
  endereco: string = '';

  responseModal: ResponseModal | null = null;

  isLoading: boolean = false;

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

  closeModal() {
    this.responseModal = null;
  }

  ngOnInit(): void {
    this.listarClientes();
  }

  listarClientes(): void {
    const usuarioLogado = this.authService.usuarioLogado;

    if (!usuarioLogado) return;

    this.changeIsLoading();

    this.compositionService.getClientesDoGerente(usuarioLogado).subscribe({
      next: (clientesDoGerente) => {
        this.clientes = clientesDoGerente;
        this.route.queryParamMap.subscribe((params) => {
          const cpf = params.get('cpf');

          if (cpf) {
            this.termoBusca = cpf;
            this.consultarCliente();
          }
        });

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

  consultarCliente(): void {
    this.changeIsLoading();
    const termoNormalizado = this.termoBusca.trim();
    const termoCpf = termoNormalizado.replace(/\D/g, '');

    if (!termoNormalizado) {
      this.clienteSelecionado = null;
      return;
    }

    this.clienteSelecionado =
      this.clientes.find((cliente) => {
        return cliente.cpf === termoCpf;
      }) ?? null;

    if (this.clienteSelecionado) {
      const enderecoSalvo = this.clienteSelecionado.endereco;
      const cepFormatado = this.formatarCep(enderecoSalvo.cep);

      this.endereco = `${cepFormatado}, ${enderecoSalvo.logradouro} - ${enderecoSalvo.cidade}/${enderecoSalvo.estado}`;
    }

    if (!this.clienteSelecionado) {
      this.responseModal = {
        title: 'Nenhum cliente encontrado',
        message: 'Verifique se você digitou o cpf corretamente',
        messageIcon: 'error',
        type: 'error',
      };
    }

    this.changeIsLoading();
  }

  formatarCpfBusca(valor: string): string {
    if (!valor) return '';
    const numeros = valor.replace(/\D/g, '').slice(0, 11);

    if (numeros.length <= 3) {
      return numeros;
    }

    if (numeros.length <= 6) {
      return `${numeros.slice(0, 3)}.${numeros.slice(3)}`;
    }

    if (numeros.length <= 9) {
      return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6)}`;
    }

    return `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`;
  }

  formatarCep(valor: string): string {
    if (!valor) return '';
    const numeros = valor.replace(/\D/g, '').slice(0, 8);

    return `${numeros.slice(0, 5)}-${numeros.slice(5, 8)}`;
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor || 0);
  }
}
