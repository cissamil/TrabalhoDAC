import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { MatTableModule } from '@angular/material/table';
import { ContaOutdated, Gerente } from '../../../core/models/entities';
import { ManagerListTableData } from '../../../core/models/table-data';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CompositionService } from '../../../core/services/compositon-services/composition-services';
import { ResponseModal } from '../../../core/models/response-modal';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';
import { MatIcon } from '@angular/material/icon';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { AdicionarGerente } from '../../../core/models/AdicionarGerente';

export interface DashboardGerenciarGerentes {
  totalGerentes: number;
  totalContas: number;
  mediaPorGerente: number;
}
@Component({
  selector: 'app-admin-gerenciar-gerentes',
  imports: [
    MatTableModule,
    NgxMaskPipe,
    FormsModule,
    NgxMaskDirective,
    MatIcon,
    MatProgressSpinner,
  ],
  templateUrl: './adm-gerenciar-gerentes.html',
  styleUrls: [
    './adm-gerenciar-gerentes.css',
    '../../shared/css/responseModal.css',
  ],
})
export class AdminGerenciarGerentes implements OnInit {
  constructor(
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private gerenteService: GerenteService,
  ) {}

  // calcularCards(): void {
  //   this.totalGerentes = this.gerentes.length;
  //   this.totalContas = this.contas.length;
  //   this.mediaPorGerente = this.totalGerentes > 0 ? this.totalContas / this.totalGerentes : 0;
  // }

  responseModal: ResponseModal | null = null;
  isLoading: boolean = false;
  loadingMessage: string = '';

  gerentes: Gerente[] = [];
  MANAGERS_TABLE: ManagerListTableData[] = [];
  dashboardGerenciarGerentes!: DashboardGerenciarGerentes;

  totalGerentes: number = 0;
  totalContas: number = 0;
  mediaPorGerente: number = 0;
  exibirFormularioNovoGerente = false;
  mensagemErro = '';
  mensagemSucesso = '';
  idGerenteEditando: string | null = null;

  novoGerente = {
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    senha: '',
  };

  displayedColunas: string[] = ['Nome', 'CPF', 'E-mail', 'Telefone', 'Ações'];

  changeIsLoading(loadingMessage?: string) {
    this.isLoading = !this.isLoading;

    if (loadingMessage) {
      this.loadingMessage = loadingMessage;
    }

    this.cdr.detectChanges();
  }

  closeModal() {
    this.responseModal = null;
  }

  ngOnInit(): void {
    this.listarGerentes();
  }

  toggleFormularioNovoGerente(): void {
    this.exibirFormularioNovoGerente = !this.exibirFormularioNovoGerente;
    this.mensagemErro = '';
    this.mensagemSucesso = '';
  }

  listarGerentes(): void {
    this.changeIsLoading('Carregando lista de gerentes');

    const token = this.authService.usuarioLogado;

    if (!token) {
      return;
    }

    this.gerenteService.listarGerentes(token).subscribe({
      next: (gerentesPegos) => {
        this.gerentes = gerentesPegos;

        console.log('Gerentes: ', this.gerentes);

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

  buscarPorId(id: string) {
    return this.gerentes.find((item) => item.gerenteId === id);
  }

  inserirNovoGerente(): void {
    this.changeIsLoading('Inserindo novo gerente');

    const token = this.authService.usuarioLogado;

    if (!token) {
      return;
    }

    const nome = this.novoGerente.nome.trim();
    const cpf = this.novoGerente.cpf.replace(/\D/g, '');
    const email = this.novoGerente.email.trim().toLowerCase();
    const telefone = this.novoGerente.telefone.replace(/\D/g, '');
    const senha = this.novoGerente.senha;

    if (!nome || !cpf || !email || !telefone || !senha) {
      this.mensagemErro = 'Preencha todos os campos, incluindo a senha.';

      this.changeIsLoading();
      return;
    }

    if (cpf.length !== 11) {
      console.log('CPF invalido. Informe 11 digitos.');
      return;
    }

    const novoGerente: AdicionarGerente = {
      cpf: cpf,
      nome: nome,
      email: email,
      telefone: telefone,
      senha: senha,
    };

    this.gerenteService.inserir(novoGerente, token).subscribe({
      next: () => {
        setTimeout(() => {
          this.responseModal = {
            title: 'Gerente Adicionado',
            message: `Gerente '${nome}' adicionado com sucesso!`,
            messageIcon: 'check',
            type: 'success',
          };

          this.listarGerentes();

          this.exibirFormularioNovoGerente = false;

          this.changeIsLoading();
        }, 800);
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

        this.changeIsLoading('');
      },
    });
  }

  remover(id: string): void {
    this.changeIsLoading('Deletando novo gerente');

    const token = this.authService.usuarioLogado;

    if (!token) {
      return;
    }

    if (
      confirm(
        `Tem certeza que deseja remover este gerente? As contas serão transferidas`,
      )
    ) {
      this.gerenteService.remover(id, token).subscribe({
        next: () => {
          setTimeout(() => {
            this.responseModal = {
              title: 'Gerente Adicionado',
              message: `Gerente removido com sucesso!`,
              messageIcon: 'check',
              type: 'success',
            };

            this.listarGerentes();

            this.changeIsLoading();
          }, 2000);
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

          this.changeIsLoading('');
        },
      });
    }

    else{
      this.changeIsLoading();
    }
  }

   atualizarGerente(id: string): void {
    //prepara a edição do gerente

    const gerente=this.buscarPorId(id);
    if (!gerente) return;

    this.idGerenteEditando = id;
    this.exibirFormularioNovoGerente = true;

    this.novoGerente = {
        nome: gerente.nome,
        cpf: gerente.cpf,
        email: gerente.email,
        telefone: gerente.telefone,
        senha: ''
      };
  }



  editarGerente(): void {

    
    const token = this.authService.usuarioLogado;
    
    if (!token) {
      return;
    }
    
    if (this.idGerenteEditando===null) return;
    
    
    const gerenteOriginal = this.buscarPorId(this.idGerenteEditando);
    
    if (!gerenteOriginal) return;
    
    this.changeIsLoading('Atualizando Gerente');

    const gerenteAtualizado: Gerente = {
      gerenteId: gerenteOriginal.gerenteId,
      cpf: gerenteOriginal.cpf,
      tipo: gerenteOriginal.tipo,
      nome: this.novoGerente.nome.trim(),
      email: this.novoGerente.email.toLowerCase(),
      telefone: this.novoGerente.telefone.replace(/\D/g, ''),
    };

    this.gerenteService.atualizar(gerenteOriginal.gerenteId, this.novoGerente, token).subscribe({
      next:()=>{

        setTimeout(() => {
          this.responseModal = {
            title: 'Gerente Adicionado',
            message: `Dados do gerente atualizados com sucesso!`,
            messageIcon: 'check',
            type: 'success',
          };

          this.listarGerentes();

          this.exibirFormularioNovoGerente = false;

          this.changeIsLoading();
        }, 800);
        
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

  salvarGerente(): void {
    if (this.idGerenteEditando !== null) {
      this.editarGerente();
    } else {
      this.inserirNovoGerente();
    }
  }
}
