import { Injectable } from '@angular/core';
import { PedidoAutoCadastro } from '../../models/entities';
import { BehaviorSubject, Observable } from 'rxjs';
import { CONTAS_MOCK, PEDIDOS_MOCK } from '../../mock/mock-data';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//const LS_CHAVE = 'pedidos-conta';

@Injectable({
  providedIn: 'root',
})
export class PedidoAutoCadastroService {

  AUTOCADASTRO_URL ="http://localhost:8080"

  httpOptions={
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    })
  }

  //private pedidosSubject: BehaviorSubject<PedidoAutoCadastro[]>;
  //public pedidos$: Observable<PedidoAutoCadastro[]>

  constructor(private httpClient : HttpClient){
   // if(!localStorage[LS_CHAVE]){
     // localStorage[LS_CHAVE] = JSON.stringify(PEDIDOS_MOCK);
    }

    // localStorage[LS_CHAVE] = JSON.stringify(PEDIDOS_MOCK);

    //const pedidos: PedidoAutoCadastro[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    //this.pedidosSubject = new BehaviorSubject<PedidoAutoCadastro[]>(pedidos);
    //this.pedidos$ = this.pedidosSubject.asObservable();

    //console.log(`MOCK de pedidos inseridos, quantidade: ${pedidos.length}`);


  // private atualizarDados(pedidos: PedidoAutoCadastro[]){
  //   return this.httpClient.put<PedidoAutoCadastro>(
  //     this.AUTOCADASTRO_URL,
  //     JSON.stringify(pedidos),
  //     this.httpOptions
  //   );
    //localStorage[LS_CHAVE] = JSON.stringify(pedidos);
    //this.pedidosSubject.next(pedidos);
  //}

  listarTodos() : Observable<PedidoAutoCadastro[]>{
    return this.httpClient.get<PedidoAutoCadastro[]>(
      this.AUTOCADASTRO_URL,
    );
  }

  inserir(pedido: PedidoAutoCadastro): Observable<PedidoAutoCadastro>{

    // console.log(`
    //   Pedido a inserir:
    //   Nome Cliente: ${pedido.nomeCliente},
    //   CPF Cliente: ${pedido.cpfCliente},
    //   CPF Gerente: ${pedido.cpfGerente},
    //   Status: ${pedido.status}
    //   Data Solicitação: ${pedido.dataSolicitacao}
    //   Salario: ${pedido.salario}
    // `);

    //pedido.id = new Date().getTime();
    //pedidos.push(pedido);
    //this.atualizarDados(pedidos);

    return this.httpClient.post<PedidoAutoCadastro>(
      this.AUTOCADASTRO_URL,
      JSON.stringify(pedido),
      this.httpOptions
    );
  }

  atualizar(pedido: PedidoAutoCadastro) : Observable<PedidoAutoCadastro>{
    // console.log(`
    //   Pedido a inserir:
    //   Cliente: ${pedido.nomeCliente},
    //   CPF Cliente: ${pedido.cpfCliente},
    //   CPF Gerente: ${pedido.cpfGerente},
    //   Status: ${pedido.status}
    //   Data Solicitação: ${pedido.dataSolicitacao}
    //   Salario: ${pedido.salario}
    // `);
    //const pedidos = this.listarTodos();

    // const index = pedidos.findIndex(p => p.id === pedido.id);
    // if(index > -1){
    //   pedidos[index] = pedido;
    //   this.atualizarDados(pedidos);
    // }
    return this.httpClient.put<PedidoAutoCadastro>(
      this.AUTOCADASTRO_URL,
      JSON.stringify(pedido),
      this.httpOptions
    )
  }



}
