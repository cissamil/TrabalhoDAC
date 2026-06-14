//ok
import { Component, OnInit } from '@angular/core';
import { NgxMaskPipe } from 'ngx-mask';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { Cliente, Conta } from '../../../core/models/entities';
import { ActivatedRoute } from '@angular/router';
import { AuthServices } from '../../../core/services/auth-services/auth-services';
import { ClienteConta } from '../../../core/models/ClienteConta';


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
    private authService: AuthServices
  ){}

  termoBusca = '';
  clienteSelecionado: ClienteConta | null = null;
  tokenJWT = ''

  ngOnInit(): void {
    this.tokenJWT=this.authService.usuarioLogado || '';

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


    this.clienteService.buscarPorCPF(termoCpf, this.tokenJWT).subscribe({
    next: (cliente: Cliente) => {
      if (!cliente) {
        this.clienteSelecionado = null;
        return;
      }

      this.contaService.buscarPorCpfCliente(cliente.cpf, this.tokenJWT).subscribe({
        next: (conta: Conta) => {
          this.clienteSelecionado = {
            id: cliente.id,
            cpf: cliente.cpf,
            nome: cliente.nome,
            email: cliente.email,
            telefone: cliente.telefone,
            salario: cliente.salario,
            endereco: cliente.endereco,

            // Embutimos a conta convertendo a 'Conta' geral para o formato 'ContaGerente'
            conta: {
              id: conta.id,
              saldo: conta.saldo ?? 0,
              limite: conta.limite ?? 0,
              gerente: { nome: conta.gerente } as any,
              cliente: cliente.nome,
              dataCriacao: conta.dataCriacao,
              cpfGerente: conta.cpfGerente,
              cpfCliente: conta.cpfCliente,
              numeroConta: conta.numeroConta,
              salario: cliente.salario
            }
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
    if (!valor) return '';
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
    }).format(valor || 0);
  }
}
