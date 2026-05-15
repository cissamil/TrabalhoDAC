import { Component, OnInit } from '@angular/core';
import { NgxMaskPipe } from 'ngx-mask';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { Cliente, Conta } from '../../../core/models/entities';
import { ActivatedRoute } from '@angular/router';

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
  imports: [FormsModule, NgxMaskPipe],
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

    this.route.queryParamMap.subscribe((params) => {
      const cpf = params.get('cpf');
      if (cpf) {
        this.termoBusca = cpf;
        this.consultarCliente();
      }
    });
  }


  consultarCliente(): void {
    const termoNormalizado = this.termoBusca.trim();
    const termoCpf = termoNormalizado.replace(/\D/g, '');

    if (!termoNormalizado) {
      this.clienteSelecionado = null;
      return;
    }


    this.clienteService.buscarPorCPF(termoCpf).subscribe({
    next: (cliente: Cliente) => {
      if (!cliente) {
        this.clienteSelecionado = null;
        return;
      }

      this.contaService.buscarPorCpfCliente(cliente.cpf).subscribe({
        next: (conta: Conta) => {
          this.clienteSelecionado = {
            ...cliente,
            saldo: conta.saldo,
            limite: conta.limite,
            numeroConta: conta.numeroConta,
            gerente: conta.gerente
          };
        },
        error: (erro: any) => console.error('Erro ao buscar conta', erro)
      });
    },
    error: (erro: any) => {
      console.error('Erro ao buscar cliente', erro);
      this.clienteSelecionado = null;
    }
  });

  }

  formatarCpfBusca(valor: string): string {
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

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(valor);
  }
}
