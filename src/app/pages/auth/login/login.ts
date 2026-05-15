import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { validateEmail } from '../../../core/shared/helpers';
import { ResponseModal } from '../../../core/models/response-modal';
//import { Cliente, Conta, GerenteAdmin } from '../../../core/models/entities';
import {ProfileOptions } from '../../../core/models/navigationOptions';
//import { ContaService } from '../../../core/services/conta-services/conta-service';
//import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
//import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
//import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';
import { AuthServices } from '../../../core/services/auth-services/auth-services';

@Component({
  selector: 'app-login',
  imports: [FormsModule, MatIconModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css',  '../../shared/css/responseModal.css'],
})
export class Login implements OnInit{
  constructor(
    private router: Router,
    //private clienteService: ClienteService,
    //private gerenteService: GerenteService,
    private clienteSessionService: ClienteSessionService,
    //private movimentacaoService: MovimentacaoService,
    //private contaService: ContaService,
    private authService: AuthServices
  ) {}

  ngOnInit(): void {

    console.log("Verificando se cliente já está logado");
    const isLogged = this.clienteSessionService.checkIfClienteIsLogged();

    if(isLogged){
      console.log("Cliente está logado, redirecionando");

      this.router.navigate(['cliente-main-page']);
    }
  }

  responseModal: ResponseModal | null = null;


  public get profileOptions(): typeof ProfileOptions {
    return ProfileOptions;
  }
  acessProfile: ProfileOptions = ProfileOptions.Cliente;

  redirect(page:string){
    this.router.navigate([page]);
  }

  setAcessProfile(option: ProfileOptions) {
    this.acessProfile = option;
  }


  closeModal(){
    if(this.responseModal?.type === 'success'){
      this.router.navigate(['/login']);
    }

    this.responseModal = null;

  }

  email: string = '';
  senha: string = '';

    private exibirErro(mensagem: string) {
      this.responseModal = {
        title: "Falha no Acesso",
        message: mensagem,
        messageIcon: "error",
        type: 'error'
      };
    }
  loginUser() {


    const verifyFields = this.validateFields();

    if(verifyFields != null){
    this.responseModal = {
        title: "Campo Inválido",
        message: verifyFields,
        messageIcon: "error",
        type: 'error'
      };
      return;
    }

    this.authService.login(this.email,this.senha).subscribe({
      next:() =>{
        const token =this.authService.usuarioLogado;
        console.log(token)
      }
    })
  }

  validateFields() : string | null{

    if(!this.email) return "Preencha o e-mail corretamente";
    if(!this.senha) return "Preencha a senha corretamente";

    if(!validateEmail(this.email)) return "Digite um e-mail válido";

    return null;

  }


  // handleResult(result : any, profile:ProfileOptions){

  //   if(result === undefined){
  //     this.responseModal = {
  //       title: "Campo Inválido",
  //       message: 'Usuário não encontrado. Verifique suas informações e tente novamente',
  //       messageIcon: "error",
  //       type: 'error'
  //     };
  //     return;
  //   }

  //   if(profile == ProfileOptions.Cliente){
  //     const cliente = result as Cliente;

  //     const conta = this.contaService.buscarPorCpfCliente(cliente.cpf).subscribe({

  //     });
  //     const movimentacoes = this.movimentacaoService.buscarMovimentacoesPorCPFCliente(cliente.cpf);

  //     this.clienteSessionService.setCliente(result as Cliente);
  //     this.clienteSessionService.setContaCliente(conta!);
  //     this.clienteSessionService.setMovimentacoesCliente(movimentacoes!);

  //     this.redirect('/cliente-main-page');
  //     return;
  //   }

  //   if(profile == ProfileOptions.Gerente){
  //     const gerente = result as GerenteAdmin;
  //     this.gerenteService.setGerenteLogado(gerente);
  //     this.redirect('/gerente-main-page');
  //     return;
  //   }

  //   if(profile == ProfileOptions.Admin){
  //     const gerente = result as GerenteAdmin;
  //     this.gerenteService.setGerenteLogado(gerente).subscribe({
  //       next:
  //       this.redirect('/admin-main-page');
  //     });
  //     this.redirect('/admin-main-page');
  //     return;
  //   }
  //}
}
