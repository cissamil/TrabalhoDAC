import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatIconModule } from '@angular/material/icon';
import { validateEmail } from '../../../core/shared/helpers';
import { ResponseModal } from '../../../core/models/response-modal';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProfileOptions } from '../../../core/models/navigationOptions';
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { HttpErrorResponse } from '@angular/common/http';
import { StandartErrorResponse } from '../../../core/models/StandartErrorResponse';

@Component({
  selector: 'app-login',
  imports: [FormsModule, MatIconModule, MatProgressSpinner],
  templateUrl: './login.html',
  styleUrls: ['./login.css', '../../shared/css/responseModal.css'],
})
export class Login implements OnInit {
  constructor(
    private router: Router,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  isLoading: boolean = false;

  changeIsLoading(){
    this.isLoading = !this.isLoading;
    this.cdr.detectChanges();
  }


  checkIfUserIsAuthenticated() {
    const decoder = new JwtHelperService();

    const storedToken = this.authService.usuarioLogado;

    if (storedToken) {
      const decodedToken = decoder.decodeToken(storedToken);

      if (!decodedToken) {
        return;
      }
      const userRole = decodedToken.role;
      
      if(userRole){
        this.redirectBasedOnUserRole(userRole);
      }
    }
  }

  redirectBasedOnUserRole(role: string) {

    console.log("Redirecionando para a tela de ", role);
    switch (role) {
      case 'CLIENTE':
        return this.router.navigate(['cliente-main-page']);
      case 'GERENTE':
        return this.router.navigate(['gerente-main-page']);
      case 'ADMIN':
        return this.router.navigate(['admin-main-page']);
      default:
        return;
    }
  }

  ngOnInit(): void {
    this.checkIfUserIsAuthenticated();
  }
  responseModal: ResponseModal | null = null;

  public get profileOptions(): typeof ProfileOptions {
    return ProfileOptions;
  }
  acessProfile: ProfileOptions = ProfileOptions.Cliente;

  redirect(page: string) {
    this.router.navigate([page]);
  }

  setAcessProfile(option: ProfileOptions) {
    this.acessProfile = option;
  }

  closeModal() {
    if (this.responseModal?.type === 'success') {
      this.router.navigate(['/login']);
    }

    this.responseModal = null;
  }

  email: string = '';
  senha: string = '';

  loginUser() {
    this.changeIsLoading();

    const verifyFields = this.validateFields();

    if (verifyFields != null) {
      this.responseModal = {
        title: 'Campo Inválido',
        message: verifyFields,
        messageIcon: 'error',
        type: 'error',
      };
      return;
    }

    this.authService.login(this.email, this.senha).subscribe({
      next: () => {
        this.checkIfUserIsAuthenticated();
        this.changeIsLoading();
      },
      error: (erro: HttpErrorResponse) => {

        console.log("Entrou no bloco error");

        console.error("Erro Interceptado: ", erro);

        const backendError = erro.error as StandartErrorResponse;

        this.responseModal = {
          title: backendError?.error || 'Erro no Cadastro',
          message:
            backendError?.message ||
            'Ocorreu um erro ao tentar se cadastrar. Tente novamente.',
          messageIcon: 'error',
          type: 'error',
        };

        this.changeIsLoading();
      },
    });
  }

  validateFields(): string | null {
    if (!this.email) return 'Preencha o e-mail corretamente';
    if (!this.senha) return 'Preencha a senha corretamente';

    if (!validateEmail(this.email)) return 'Digite um e-mail válido';

    return null;
  }

}
