import { Component, OnInit } from '@angular/core';
import { NgxMaskPipe, NgxMaskDirective } from 'ngx-mask';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { Cliente, Conta } from '../../../core/models/entities';
import { ActivatedRoute } from '@angular/router';
import { CLIENTES_MOCK, CONTAS_MOCK } from '../../../core/mock/mock-data';

interface ClienteDetalhado {
  cpf: string;
  nome: string;
  email: string;
  telefone:string
  endereco: string;
  salario: number;
  saldo: number;
  limite: number;
  numeroConta: number;
  gerente: string;
}

@Component({
  selector: 'app-cliente-especifico',
  imports: [FormsModule, NgxMaskPipe, NgxMaskDirective],
  templateUrl: './cliente-especifico.html',
  styleUrl: './cliente-especifico.css',
})
export class ClienteEspecifico implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private clienteService: ClienteService,
    private contaService: ContaService,
  ){}

  clientes:Cliente[]=[];
  contas: Conta[]=[];

  termoBusca = '';
  clienteSelecionado: ClienteDetalhado | null = null;


  ngOnInit(): void {
    this.clientes=this.clienteService.listarTodos();
    this.contas=this.contaService.listarTodos();
    this.route.queryParamMap.subscribe((params) => {
      const cpf = params.get('cpf');
      if (cpf) {
        this.termoBusca = cpf;
        this.consultarCliente();
      }
    });
  }

  consultarCliente(): void {
    const termoNormalizado = this.termoBusca.trim().toLowerCase();
    if (!termoNormalizado) {
      this.clienteSelecionado = null;
      return;
    }

    const cliente = this.clientes.find(
      (item) => item.cpf.includes(termoNormalizado));
    const termoCpf = termoNormalizado.replace(/\D/g, '');

    if (!cliente) {
      this.clienteSelecionado = null;
      return;
    }

    const conta = this.contas.find((item) => item.cpfCliente === cliente.cpf);
    if (!conta) {
      this.clienteSelecionado = null;
      return;
    }

    this.clienteSelecionado = {
      cpf: cliente.cpf,
      nome: cliente.nome,
      email: cliente.email,
      telefone:cliente.telefone,
      endereco: cliente.endereco,
      salario: cliente.salario,
      saldo: conta.saldo,
      limite: conta.limite,
      numeroConta: conta.numeroConta,
      gerente: conta.gerente,
    };
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }
}
