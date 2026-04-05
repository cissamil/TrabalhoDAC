import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { Cliente, Conta } from '../../../core/models/entities';

interface ClienteDetalhado {
  cpf: string;
  nome: string;
  email: string;
  endereco: string;
  salario: number;
  saldo: number;
  limite: number;
  numeroConta: number;
  gerente: string;
}

@Component({
  selector: 'app-cliente-especifico',
  imports: [FormsModule],
  templateUrl: './cliente-especifico.html',
  styleUrl: './cliente-especifico.css',
})
export class ClienteEspecifico implements OnInit {

  constructor(
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

  }

  consultarCliente(): void {
    const termoNormalizado = this.termoBusca.trim().toLowerCase();
    if (!termoNormalizado) {
      this.clienteSelecionado = null;
      return;
    }

    const cliente = this.clientes.find(
      (item) => item.cpf.includes(termoNormalizado));

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
