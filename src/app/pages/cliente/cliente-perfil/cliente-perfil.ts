import { Component } from '@angular/core';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../mock/mock-data';
import { FormsModule } from "@angular/forms";
import { DecimalPipe } from '@angular/common';
import { NgxMaskDirective } from "ngx-mask";

@Component({
  selector: 'app-cliente-perfil',
  imports: [FormsModule, DecimalPipe, NgxMaskDirective],
  templateUrl: './cliente-perfil.html',
  styleUrl: './cliente-perfil.css',
})
export class ClientePerfil {

  cliente = CLIENTES_MOCK[0];
  conta_cliente = CONTAS_MOCK[0];
  
  endereco = this.cliente.endereco.split(' - ');

  cep = this.endereco[0];
  rua = this.endereco[1];
  cidade = this.endereco[2];
  estado = this.endereco[3];


  


}
