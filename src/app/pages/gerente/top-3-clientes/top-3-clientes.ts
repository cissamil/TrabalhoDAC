import { ChangeDetectorRef, Component, OnDestroy, OnInit, inject } from '@angular/core';
import { Subscription, combineLatest } from 'rxjs';
import { ClienteOutdated, ContaOutdated } from '../../../core/models/entities';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CommonModule } from '@angular/common';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { MelhoresClientes } from '../../../core/models/MelhoresClientes';
import { ResponseModal } from '../../../core/models/response-modal';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { MatIcon } from "@angular/material/icon";
import { MatProgressSpinner } from "@angular/material/progress-spinner";

interface TopCliente {
  cpf: string;
  nome: string;
  cidade: string;
  estado: string;
  saldo: number;
}

@Component({
  selector: 'app-top-3-clientes',
  imports: [CommonModule, MatIcon, MatProgressSpinner],
  templateUrl: './top-3-clientes.html',
  styleUrls: ['./top-3-clientes.css', '../../shared/css/responseModal.css'],
})
export class Top3Clientes implements OnInit, OnDestroy {
  constructor(
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private compositionService: CompositionService,
  ){}

  private readonly subscriptions = new Subscription();
  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;

  topClientes: MelhoresClientes[] = [];

  changeIsLoading() {
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }

  closeModal() {
    this.responseModal = null;
  }

  ngOnInit(): void {

    const usuarioLogado = this.authService.usuarioLogado;

    if (!usuarioLogado) return;


    this.changeIsLoading();

    this.compositionService.getMelhoresClientes(usuarioLogado).subscribe({
      next: (melhoreClientes) =>{
        
        this.topClientes = melhoreClientes;


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
    })
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  formatarCpf(cpf: string): string {
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
}
