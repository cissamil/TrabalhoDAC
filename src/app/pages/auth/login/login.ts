import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { validateEmail } from '../../../core/shared/helpers';
import { ResponseModal } from '../../../core/models/response-modal';
import { Cliente, GerenteAdmin } from '../../../core/models/entities';
import {ProfileOptions } from '../../../core/models/navigationOptions';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { GerenteService } from '../../../core/services/gerente-services/gerente-services';
import { ClienteSessionService } from '../../../core/services/session-controller.service';
import { MovimentacaoService } from '../../../core/services/movimentacoes-service/movimentacao-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, MatIconModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css',  '../../shared/css/responseModal.css'],
})
export class Login implements OnInit{
  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private gerenteService: GerenteService,
    private clienteSessionService: ClienteSessionService,
    private movimentacoesService: MovimentacaoService,
    private contaService: ContaService
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

  loginUser() {
    // console.log(`Email inserido: ${this.email}. Senha inserida: ${this.senha}`);

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

    if(this.acessProfile == ProfileOptions.Cliente){
      // console.log('validando dado cliente');
      const result = this.clienteService.buscarClientePorEmailESenha(
        this.email,
        this.senha,
      );
      // console.log('result cliente: ', result);

      this.handleResult(result, this.acessProfile);
    }

    if(this.acessProfile === ProfileOptions.Gerente){
      // console.log('validando dado gerente');
      const result = this.gerenteService.buscarGerentePorEmailESenhaETipo(
        this.email,
        this.senha,
        "gerente"
      );
      // console.log('result ger: ', result);

      this.handleResult(result, this.acessProfile);
    }

    if(this.acessProfile === ProfileOptions.Admin){
      // console.log('validando dado admin');
      const result = this.gerenteService.buscarGerentePorEmailESenhaETipo(
        this.email,
        this.senha,
        "administrador"
      );
      // console.log('result adm: ', result);

      this.handleResult(result, this.acessProfile);
    }

  }

  validateFields() : string | null{

    if(!this.email) return "Preencha o e-mail corretamente";
    if(!this.senha) return "Preencha a senha corretamente";

    if(!validateEmail(this.email)) return "Digite um e-mail válido";

    return null;

  }



  handleResult(result : any, profile:ProfileOptions){

    if(result === undefined){
      this.responseModal = {
        title: "Campo Inválido",
        message: 'Usuário não encontrado. Verifique suas informações e tente novamente',
        messageIcon: "error",
        type: 'error'
      };
      return;
    }

    if(profile == ProfileOptions.Cliente){
      const cliente = result as Cliente;

      const conta = this.contaService.buscarPorCpfCliente(cliente.cpf);
      // const movimentacoes = this.movimentacaoService.buscarMovimentacoesPorCPFCliente(cliente.cpf);

      this.clienteSessionService.setCliente(result as Cliente);
      this.clienteSessionService.setContaCliente(conta!);
      // this.clienteSessionService.setMovimentacoesCliente(movimentacoes!);

      this.redirect('/cliente-main-page');
      return;
    }

    if(profile == ProfileOptions.Gerente){
      const gerente = result as GerenteAdmin;
      this.gerenteService.setGerenteLogado(gerente);
      this.redirect('/gerente-main-page');
      return;
    }

    if(profile == ProfileOptions.Admin){
      const gerente = result as GerenteAdmin;
      this.gerenteService.setGerenteLogado(gerente);
      this.redirect('/admin-main-page');
      return;
    }
  }
}
