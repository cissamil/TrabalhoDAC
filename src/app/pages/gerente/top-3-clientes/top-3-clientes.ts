import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { Subscription, combineLatest } from 'rxjs';
import { ClienteOutdated, ContaOutdated } from '../../../core/models/entities';
import { ClienteService } from '../../../core/services/cliente-services/cliente-service';
import { ContaService } from '../../../core/services/conta-services/conta-service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../core/services/auth-services/auth-services';
import { CommonModule } from '@angular/common';

interface TopCliente {
  cpf: string;
  nome: string;
  cidade: string;
  estado: string;
  saldo: number;
}

@Component({
  selector: 'app-top-3-clientes',
  imports: [CommonModule],
  templateUrl: './top-3-clientes.html',
  styleUrl: './top-3-clientes.css',
})
export class Top3Clientes implements OnInit, OnDestroy {
  constructor(
    private route: ActivatedRoute,
    private clienteService: ClienteService,
    private contaService: ContaService,
    private authService: AuthService
  ){}
  private readonly subscriptions = new Subscription();

  topClientes: TopCliente[] = [];
  tokenJWT = ''

  ngOnInit(): void {
    this.tokenJWT=this.authService.usuarioLogado || '';


    // this.subscriptions.add(
    //   combineLatest([
    //     this.clienteService.listarTodos(this.tokenJWT),
    //     this.contaService.listarTodos(this.tokenJWT)
    //   ]).subscribe({
    //     next: ([clientes, contas]: [Cliente[], Conta[]])=>{
    //       const clientesPorCpf=new Map<string, Cliente>(
    //         clientes.map((cliente)=> [cliente.cpf, cliente])
    //       );
    //       this.topClientes=contas.map((conta)=>{
    //         const cliente = clientesPorCpf.get(conta.cpfCliente);
    //         if (!cliente) return null;

    //         const { cidade, estado } = this.extrairCidadeEstado(cliente.endereco || '');
    //           return {
    //             cpf: cliente.cpf,
    //             nome: cliente.nome,
    //             cidade,
    //             estado,
    //             saldo: conta.saldo ?? 0,
    //           };
    //       })
    //       .filter((item): item is TopCliente => item !== null)
    //       .sort((a, b) => b.saldo - a.saldo)
    //       .slice(0, 3);
    //     },
    //     error: (err) => console.error('Erro ao buscar ranking de clientes:', err)
    //     })
    // );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  private extrairCidadeEstado(endereco: string): { cidade: string; estado: string } {
    if (!endereco || !endereco.includes(' - ')) {
      return { cidade: 'Não Informada', estado: '-' };
    }
    const partes = endereco.split(' - ').map((item) => item.trim());
    const estado = partes.at(-1) ?? '-';
    const cidade = partes.at(-2) ?? '-';

    return { cidade, estado };
  }

  formatarCpf(cpf: string): string {
    const numeros = cpf.replace(/\D/g, '');
    if (numeros.length !== 11) {
      return cpf;
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
