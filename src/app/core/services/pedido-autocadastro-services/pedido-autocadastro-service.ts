import { Injectable } from '@angular/core';
import { PedidoAutoCadastro } from '../../models/entities';
import { BehaviorSubject, Observable } from 'rxjs';
import { CONTAS_MOCK, PEDIDOS_MOCK } from '../../mock/mock-data';

const LS_CHAVE = 'pedidos-conta';

@Injectable({
  providedIn: 'root',
})
export class PedidoAutoCadastroService {


  private pedidosSubject: BehaviorSubject<PedidoAutoCadastro[]>;
  public pedidos$: Observable<PedidoAutoCadastro[]>
  
  constructor(){
    if(!localStorage[LS_CHAVE]){

      localStorage[LS_CHAVE] = JSON.stringify(PEDIDOS_MOCK); 
    }
      
    // localStorage[LS_CHAVE] = JSON.stringify(PEDIDOS_MOCK); 

    const pedidos: PedidoAutoCadastro[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    this.pedidosSubject = new BehaviorSubject<PedidoAutoCadastro[]>(pedidos);
    this.pedidos$ = this.pedidosSubject.asObservable();

    console.log(`MOCK de pedidos inseridos, quantidade: ${pedidos.length}`);
  }

  private atualizarDados(pedidos: PedidoAutoCadastro[]){
    localStorage[LS_CHAVE] = JSON.stringify(pedidos);
    this.pedidosSubject.next(pedidos);
  }  

  listarTodos() : PedidoAutoCadastro[]{

    return this.pedidosSubject.getValue();
  }

  inserir(pedido: PedidoAutoCadastro): void{

    // console.log(`
    //   Pedido a inserir:
    //   Nome Cliente: ${pedido.nomeCliente},
    //   CPF Cliente: ${pedido.cpfCliente},
    //   CPF Gerente: ${pedido.cpfGerente},
    //   Status: ${pedido.status}
    //   Data Solicitação: ${pedido.dataSolicitacao}
    //   Salario: ${pedido.salario}
    // `);

    const pedidos = this.listarTodos();
    pedido.id = new Date().getTime();
    pedidos.push(pedido);
    this.atualizarDados(pedidos);
  }

  atualizar(pedido: PedidoAutoCadastro) : void{
    // console.log(`
    //   Pedido a inserir:
    //   Cliente: ${pedido.nomeCliente},
    //   CPF Cliente: ${pedido.cpfCliente},
    //   CPF Gerente: ${pedido.cpfGerente},
    //   Status: ${pedido.status}
    //   Data Solicitação: ${pedido.dataSolicitacao}
    //   Salario: ${pedido.salario}
    // `);
    const pedidos = this.listarTodos();
    
    const index = pedidos.findIndex(p => p.id === pedido.id);
    if(index > -1){
      pedidos[index] = pedido;
      this.atualizarDados(pedidos);
    }
  }


  
}
