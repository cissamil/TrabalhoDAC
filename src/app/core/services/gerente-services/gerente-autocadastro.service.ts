import { Injectable } from '@angular/core';
import { ClienteService } from '../cliente-services/cliente-service';
import { ContaOutdated, EmailNotificacao, GerenteAdmin, PedidoAutoCadastro } from '../../models/entities';
import { PedidoAutoCadastroService } from '../pedido-autocadastro-services/pedido-autocadastro-service';
import { ContaService } from '../conta-services/conta-service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GerenteAutocadastroService {
  API_CONTAS_URL="http://localhost:8080/contas";

  constructor(
    private httpClient: HttpClient,
  ){}

  pedidos: PedidoAutoCadastro[] = [];
  contasCriadas: ContaOutdated[] = [];
  emailsEnviados: EmailNotificacao[] = [];


    public aprovarConta(idCliente: string, token: string): Observable<void>{
      const httpOptions = {
        headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        })
      }
      return this.httpClient.put<void>(
        // `${this.API_CONTAS_URL}${idConta}/status`,
        `${this.API_CONTAS_URL}/${idCliente}/aprovar`,
        {status: 'CONTA_APROVADA'},
        httpOptions

      );
    }

  public recusarConta(idCliente: string, motivo: string, token: string): Observable<void>{
    const httpOptions = {
        headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        })
      }
    return this.httpClient.put<void>(
      // `${this.API_CONTAS_URL}/${idConta}/status`,
      `${this.API_CONTAS_URL}/${idCliente}/rejeitar`,
      {status: 'CONTA_REJEITADA', motivoRecusa:motivo},
      httpOptions
    );
  }

  private registrarEmailAprovacao(pedido: PedidoAutoCadastro, conta: ContaOutdated, senha: string): void {
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
