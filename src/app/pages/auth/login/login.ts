import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { FormsModule } from '@angular/forms';
import { validateEmail } from '../../../core/shared/helpers';
import {ProfileOptions } from '../../../core/models/navigationOptions';
import { GerenteService } from '../../../core/services/gerente-services';
import { Cliente, GerenteAdmin } from '../../../core/models/entities';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private gerenteService: GerenteService,
  ) {}

  public get profileOptions(): typeof ProfileOptions {
    return ProfileOptions;
  }
  acessProfile: ProfileOptions = ProfileOptions.Cliente;

  redirect(page:string){
    this.router.navigate([page]);
  }

  setAcessProfile(option: ProfileOptions) {
    this.acessProfile = option;
    console.log('AcessProfile', this.acessProfile);
  }

  email: string = '';
  senha: string = '';

  loginUser() {
    console.log(`Email inserido: ${this.email}. Senha inserida: ${this.senha}`);

    if (!this.email || !this.senha) {
      alert('Preencha todos os campos');
      return;
    }

    if (!validateEmail(this.email)) {
      alert('Digite um email válido');
      return;
    }
    
    if(this.acessProfile == ProfileOptions.Cliente){
      const result = this.clienteService.buscarClientePorEmailESenha(
        this.email,
        this.senha,
      );

      this.handleResult(result, this.acessProfile);
    }

    if(this.acessProfile === ProfileOptions.Gerente){
      const result = this.gerenteService.buscarGerentePorEmailESenhaETipo(
        this.email,
        this.senha,
        "gerente"
      );

      this.handleResult(result, this.acessProfile);
    }
    
    if(this.acessProfile === ProfileOptions.Admin){
      const result = this.gerenteService.buscarGerentePorEmailESenhaETipo(
        this.email,
        this.senha,
        "administrador"
      );

      this.handleResult(result, this.acessProfile);
    }
    
  }

  handleResult(result : any, profile:ProfileOptions){
    
    if(result == undefined){
      alert("Usuário não encontrado. Tente novamente");
      return;
    }

    if(profile == ProfileOptions.Cliente){
      this.redirect('/cliente-main-page');
      return;
    }

    if(profile == ProfileOptions.Gerente){
      this.redirect('/gerente-main-page');
      return;
    }
    
    if(profile == ProfileOptions.Admin){
      this.redirect('/admin-main-page');
      return;
    }
  }
}
