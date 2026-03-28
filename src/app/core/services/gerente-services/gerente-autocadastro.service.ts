import { Injectable } from '@angular/core';

export type PedidoStatus = 'PENDENTE' | 'APROVADO' | 'RECUSADO';

export interface ContaGerada {
  cpfCliente: string;
  numeroConta: string;
  senha: string;
  limite: number;
  gerente: string;
  dataCriacao: Date;
}

export interface PedidoAutocadastro {
  cpf: string;
  nome: string;
  email: string;
  salario: number;
  dataSolicitacao: Date;
  status: PedidoStatus;
  dataDecisao?: Date;
  motivoRecusa?: string;
  contaGerada?: ContaGerada;
}

export interface EmailNotificacao {
  para: string;
  assunto: string;
  corpo: string;
  dataEnvio: Date;
}

@Injectable({
  providedIn: 'root',
})
export class GerenteAutocadastroService {
  private readonly pedidos: PedidoAutocadastro[] = [
    {
      cpf: '41826991007',
      nome: 'Amanda Costa',
      email: 'amanda.costa@email.com',
      salario: 2400,
      dataSolicitacao: new Date('2026-03-25T09:20:00'),
      status: 'PENDENTE',
    },
    {
      cpf: '98256430060',
      nome: 'Renato Alves',
      email: 'renato.alves@email.com',
      salario: 1700,
      dataSolicitacao: new Date('2026-03-26T11:45:00'),
      status: 'PENDENTE',
    },
    {
      cpf: '14690238044',
      nome: 'Patricia Ramos',
      email: 'patricia.ramos@email.com',
      salario: 5200,
      dataSolicitacao: new Date('2026-03-26T15:30:00'),
      status: 'PENDENTE',
    },
  ];

  private readonly contasCriadas: ContaGerada[] = [];
  private readonly emailsEnviados: EmailNotificacao[] = [];

  getPedidosPendentes(): PedidoAutocadastro[] {
    return this.pedidos.filter((pedido) => pedido.status === 'PENDENTE');
  }

  getPedidosProcessados(): PedidoAutocadastro[] {
    return this.pedidos
      .filter((pedido) => pedido.status !== 'PENDENTE')
      .sort((a, b) => (b.dataDecisao?.getTime() ?? 0) - (a.dataDecisao?.getTime() ?? 0));
  }

  getEmailsEnviados(): EmailNotificacao[] {
    return this.emailsEnviados
      .slice()
      .sort((a, b) => b.dataEnvio.getTime() - a.dataEnvio.getTime());
  }

  aprovarPedido(cpf: string, gerente: string): ContaGerada | null {
    const pedido = this.pedidos.find((item) => item.cpf === cpf && item.status === 'PENDENTE');
    if (!pedido) {
      return null;
    }

    const contaGerada: ContaGerada = {
      cpfCliente: pedido.cpf,
      numeroConta: this.gerarNumeroConta(),
      senha: this.gerarSenhaTemporaria(),
      limite: this.calcularLimite(pedido.salario),
      gerente,
      dataCriacao: new Date(),
    };

    pedido.status = 'APROVADO';
    pedido.dataDecisao = new Date();
    pedido.contaGerada = contaGerada;

    this.contasCriadas.push(contaGerada);
    this.registrarEmailAprovacao(pedido, contaGerada);

    return contaGerada;
  }

  recusarPedido(cpf: string, motivoRecusa: string): boolean {
    const pedido = this.pedidos.find((item) => item.cpf === cpf && item.status === 'PENDENTE');
    if (!pedido) {
      return false;
    }

    pedido.status = 'RECUSADO';
    pedido.dataDecisao = new Date();
    pedido.motivoRecusa = motivoRecusa;

    this.registrarEmailRecusa(pedido, motivoRecusa);
    return true;
  }

  private calcularLimite(salario: number): number {
    return salario >= 2000 ? salario / 2 : 0;
  }

  private gerarNumeroConta(): string {
    return Math.floor(1000 + Math.random() * 9000).toString();
  }

  private gerarSenhaTemporaria(): string {
    const caracteres = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789';
    let senha = '';

    for (let i = 0; i < 10; i += 1) {
      const indice = Math.floor(Math.random() * caracteres.length);
      senha += caracteres[indice];
    }

    return senha;
  }

  private registrarEmailAprovacao(pedido: PedidoAutocadastro, conta: ContaGerada): void {
    const limiteFormatado = new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(conta.limite);

    this.emailsEnviados.push({
      para: pedido.email,
      assunto: 'Aprovacao de autocadastro',
      corpo: `Ola ${pedido.nome}, sua conta foi aprovada. Numero da conta: ${conta.numeroConta}. Senha temporaria: ${conta.senha}. Limite: ${limiteFormatado}.`,
      dataEnvio: new Date(),
    });
  }

  private registrarEmailRecusa(pedido: PedidoAutocadastro, motivo: string): void {
    this.emailsEnviados.push({
      para: pedido.email,
      assunto: 'Recusa de autocadastro',
      corpo: `Ola ${pedido.nome}, seu autocadastro foi recusado. Motivo: ${motivo}.`,
      dataEnvio: new Date(),
    });
  }
}
