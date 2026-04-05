import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Movimentacao } from '../../models/entities';
import { MOVIMENTACOES_MOCK } from '../../mock/mock-data';
import { ClienteSessionService } from '../session-controller.service';

const LS_CHAVE = 'movimentacoes';
@Injectable({
  providedIn: 'root',
})
export class MovimentacaoService {

  private movimentacoesSubject: BehaviorSubject<Movimentacao[]>;
  public movimentacoes$: Observable<Movimentacao[]>
  
  
  // movimentacao-service.ts

  constructor() {
    const dadosLocalStorage = localStorage.getItem(LS_CHAVE);
    
    // 1. Tenta converter o que existe
    let movimentacoesExistentes: Movimentacao[] = [];
    if (dadosLocalStorage) {
      try {
        movimentacoesExistentes = JSON.parse(dadosLocalStorage);
      } catch (e) {
        movimentacoesExistentes = [];
      }
    }

    // 2. Se não existir NADA ou o array estiver vazio, aí sim coloca o MOCK
    if (!dadosLocalStorage || (Array.isArray(movimentacoesExistentes) && movimentacoesExistentes.length === 0)) {
      console.log("LocalStorage vazio ou zerado, inserindo MOCK...");
      localStorage.setItem(LS_CHAVE, JSON.stringify(MOVIMENTACOES_MOCK));
      movimentacoesExistentes = MOVIMENTACOES_MOCK;
    }

    // 3. Inicializa o Subject com os dados garantidos
    this.movimentacoesSubject = new BehaviorSubject<Movimentacao[]>(movimentacoesExistentes);
    this.movimentacoes$ = this.movimentacoesSubject.asObservable();

    console.log(`MOCK de movimentacoes carregados, quantidade: ${movimentacoesExistentes.length}`);
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

    // this.clienteSessionService.setMovimentacoesCliente(movimentacoes);
    this.atualizarDados(movimentacoes);
    this.listarMovimentacoes();
  }


  buscarMovimentacoesPorCPFCliente(cpfCliente:string): Movimentacao[]{
    const movimentacoes = this.listarTodos();

    return movimentacoes.filter(
      (movimentacao) =>
        movimentacao.cpfClienteOrigem == cpfCliente ||
        movimentacao.cpfClienteDestino == cpfCliente,
    );
  }

  listarMovimentacoes(){
    const movimentacoes = this.listarTodos();

    const movimentacoesClientes = movimentacoes.filter((m) => m.clienteOrigem === "Catianna");

    console.log("Movimentações da Catianna: ", movimentacoesClientes);
  }
  
}
