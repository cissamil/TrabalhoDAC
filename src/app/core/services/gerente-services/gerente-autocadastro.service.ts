import { inject, Injectable } from '@angular/core';
import { ClienteService } from '../cliente-services/cliente-service';
import { Conta, EmailNotificacao, GerenteAdmin, PedidoAutoCadastro } from '../../models/entities';
import { PedidoAutoCadastroService } from '../pedido-autocadastro-services/pedido-autocadastro-service';
import { ContaService } from '../conta-services/conta-service';

@Injectable({
  providedIn: 'root',
})
export class GerenteAutocadastroService {

  private readonly pedidosService = inject(PedidoAutoCadastroService);
  private readonly clientesService = inject(ClienteService);
  private readonly contaService = inject(ContaService);

  private readonly pedidos: PedidoAutoCadastro[] = [];
  private readonly contasCriadas: Conta[] = [];
  private readonly emailsEnviados: EmailNotificacao[] = [];

  getPedidosPendentes(cpfGerente:string): PedidoAutoCadastro[] {
    return this.pedidos.filter(
      (pedido) =>
        pedido.status === 'PENDENTE' && pedido.cpfGerente === cpfGerente,
    );
  }

  getPedidosProcessados(cpfGerente: string): PedidoAutoCadastro[] {
    return this.pedidos
      .filter((pedido) => pedido.status !== 'PENDENTE' && pedido.cpfGerente === cpfGerente)
      .sort((a, b) =>{

        const dataA = a.dataDecisao? new Date(a.dataDecisao).getTime() : 0;
        const dataB = b.dataDecisao? new Date(b.dataDecisao).getTime() : 0;

        return dataB - dataA;
      });
  }

  getEmailsEnviados(): EmailNotificacao[] {
    return this.emailsEnviados
      .slice()
      .sort((a, b) => b.dataEnvio.getTime() - a.dataEnvio.getTime());
  }

  aprovarPedido(cpf: string, gerente: GerenteAdmin): Conta | null {
    const pedido = this.pedidos.find((item) => item.cpfCliente === cpf && item.status === 'PENDENTE');
    const cliente = this.clientesService.buscarPorCPF(cpf);

    console.log("CPF:", cpf);
    //console.log(`pedido: ${pedido?.id}. Cliente: ${cliente?.id}`);

    if (!pedido || !cliente) {
      return null;
    }

    const senha:string = this.gerarSenhaTemporaria();

    //cliente.senha = senha;

    //this.clientesService.atualizar(cliente);


    //const contaGerada: Conta = {
    //   id: new Date().getTime(),
    //   //cliente: cliente.nome,
    //   cpfCliente: pedido.cpfCliente,
    //   numeroConta: this.gerarNumeroConta(),
    //   limite: this.calcularLimite(pedido.salario),
    //   gerente: gerente.nome,
    //   cpfGerente: gerente.cpf,
    //   saldo: 0,
    //   dataCriacao: new Date(),
    // };

    pedido.status = 'APROVADO';
    pedido.dataDecisao = new Date();
    //pedido.contaGerada = contaGerada;

    this.pedidosService.atualizar(pedido);
    //this.contasCriadas.push(contaGerada);
    //this.registrarEmailAprovacao(pedido, contaGerada, senha);

    //this.contaService.inserir(contaGerada);

    console.log("Senha gerada para o usuário: ", senha);
    //return contaGerada;
    return null;
  }

  recusarPedido(cpf: string, motivoRecusa: string): boolean {
    const pedido = this.pedidos.find((item) => item.cpfCliente === cpf && item.status === 'PENDENTE');
    if (!pedido) {
      return false;
    }

    pedido.status = 'RECUSADO';
    pedido.dataDecisao = new Date();
    pedido.motivoRecusa = motivoRecusa;

    this.pedidosService.atualizar(pedido);
    this.registrarEmailRecusa(pedido, motivoRecusa);
    return true;
  }

  private calcularLimite(salario: number): number {
    return salario >= 2000 ? salario / 2 : 0;
  }

  private gerarNumeroConta(): number {
    return Math.floor(1000 + Math.random() * 9000);
  }

  private gerarSenhaTemporaria(): string {
    const caracteres = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz0123456789';
    let senha = '';

    for (let i = 0; i < 10; i += 1) {
      const indice = Math.floor(Math.random() * caracteres.length);
      senha += caracteres[indice];
    }

    return senha;
  }

  private registrarEmailAprovacao(pedido: PedidoAutoCadastro, conta: Conta, senha: string): void {
    const limiteFormatado = new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(conta.limite);

    this.emailsEnviados.push({
      para: pedido.emailCliente,
      assunto: 'Aprovacao de autocadastro',
      corpo: `Ola ${pedido.nomeCliente}, sua conta foi aprovada. Numero da conta: ${conta.numeroConta}. Senha temporaria: ${senha}. Limite: ${limiteFormatado}.`,
      dataEnvio: new Date(),
    });
  };

  private registrarEmailRecusa(pedido: PedidoAutoCadastro, motivo: string): void {
    this.emailsEnviados.push({
      para: pedido.emailCliente,
      assunto: 'Recusa de autocadastro',
      corpo: `Ola ${pedido.nomeCliente}, seu autocadastro foi recusado. Motivo: ${motivo}.`,
      dataEnvio: new Date(),
    });
  }
}
