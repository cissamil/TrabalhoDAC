import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Movimentacao } from '../../models/entities';
import { MOVIMENTACOES_MOCK } from '../../mock/mock-data';

const LS_CHAVE = 'movimentacoes';
@Injectable({
  providedIn: 'root',
})
export class MovimentacaoService {

    private movimentacoesSubject: BehaviorSubject<Movimentacao[]>;
    public movimentacoes$: Observable<Movimentacao[]>
  
  
    constructor(){
      if(!localStorage[LS_CHAVE]){
  
        localStorage[LS_CHAVE] = JSON.stringify(MOVIMENTACOES_MOCK); 
      }
      
      const movimentacoes: Movimentacao[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
      this.movimentacoesSubject = new BehaviorSubject<Movimentacao[]>(movimentacoes);
      this.movimentacoes$ = this.movimentacoesSubject.asObservable();
  
      console.log(`MOCK de movimentacoes inseridos, quantidade: ${movimentacoes.length}`);
    }
  
    private atualizarDados(movimentacoes: Movimentacao[]){
      localStorage[LS_CHAVE] = JSON.stringify(movimentacoes);
      this.movimentacoesSubject.next(movimentacoes);
    }  
  
    listarTodos() : Movimentacao[]{
      return this.movimentacoesSubject.getValue();
    }
  
    inserir(movimentacao:Movimentacao): void{ 
  
      console.log(`
        Movimentacao a inserir:
        tipo: ${movimentacao.tipo}
        clienteOrigem: ${movimentacao.clienteOrigem}
        clienteDestino: ${movimentacao.clienteDestino}
        data_hora: ${movimentacao.data_hora}
        valor: ${movimentacao.valor}
      `);
  
      const movimentacoes = this.listarTodos();
      movimentacao.id = new Date().getTime();
      movimentacao.data_hora = new Date();
      movimentacoes.push(movimentacao);
      this.atualizarDados(movimentacoes);
      this.listarMovimentacoes();
    }
  
  
    buscarPorId(){
  
    }

    listarMovimentacoes(){
      const movimentacoes = this.listarTodos();

      const movimentacoesClientes = movimentacoes.filter((m) => m.clienteOrigem === "Catianna");

      console.log("Movimentações da Catianna: ", movimentacoesClientes);
    }
  
}
