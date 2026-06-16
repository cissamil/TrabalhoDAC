import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
//import { CONTAS_MOCK } from '../../mock/mock-data';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContaAprovar } from '../../models/ContaAprovar';
import { ContaDeposito } from '../../models/ContaDeposito';
import { ContaCliente } from '../../models/ContaGerente';
import { ContaSaque } from '../../models/ContaSaque';
import { ContaTransferencia } from '../../models/ContaTransferencia';
import { Conta, ContaGerada, ContaOutdated } from '../../models/entities';
import { ContaRejeitar } from '../../models/ContaRejeitar';
//import { ClienteService } from '../cliente-services/cliente-service';

//const LS_CHAVE = 'contas';

@Injectable({
  providedIn: 'root',
})
export class ContaService {
  CONTA_URL = 'http://localhost:8080/contas';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private httpClient: HttpClient) {}

  inserir(conta: ContaOutdated): Observable<ContaOutdated> {
    return this.httpClient.post<ContaOutdated>(
      this.CONTA_URL,
      JSON.stringify(conta),
      this.httpOptions,
    );
  }

  depositarValor(conta: ContaDeposito, token: string): Observable<void> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<void>(
      this.CONTA_URL + '/depositar',
      JSON.stringify(conta),
      { headers: header },
    );
  }

  sacarValor(conta: ContaSaque, token: string): Observable<void> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<void>(
      this.CONTA_URL + '/sacar',
      JSON.stringify(conta),
      { headers: header },
    );
  }

  transferirValor(conta: ContaTransferencia, token: string): Observable<void> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<void>(
      this.CONTA_URL + '/transferir',
      JSON.stringify(conta),
      { headers: header },
    );
  }

  aprovarConta(
    contaId: string,
    contaAprovar: ContaAprovar,
    token: string,
  ): Observable<void> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<void>(
      `${this.CONTA_URL}/${contaId}/aprovar`,
      JSON.stringify(contaAprovar),
      { headers: header },
    );
  }

  rejeitarConta(
    contaId: string,
    contaRejeitar: ContaRejeitar,
    token: string,
  ): Observable<void> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<void>(
      `${this.CONTA_URL}/${contaId}/rejeitar`,
      JSON.stringify(contaRejeitar),
      { headers: header },
    );
  }

  listarConta(numeroConta: number): Observable<ContaGerada[]> {
    return this.httpClient.get<ContaGerada[]>(
      this.CONTA_URL + '/numero/' + numeroConta,
    );
  }

  listarTodos(token: string): Observable<ContaCliente[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`, // Garante que o gateway/microserviço receba a autenticação
      }),
    };

    return this.httpClient.get<ContaCliente[]>(
      `${this.CONTA_URL}`,
      httpOptions,
    );
  }

  buscarPorClienteId(clienteId: string, token: string): Observable<Conta> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get<Conta>(
      this.CONTA_URL + '/' + clienteId,
      this.httpOptions,
    );
  }

  buscarPorId(id: number): Observable<ContaOutdated> {
    return this.httpClient.get<ContaOutdated>(
      this.CONTA_URL + '/' + id,
      this.httpOptions,
    );
  }
}
