import { Injectable } from '@angular/core';
import { Conta } from '../../models/entities';
import { BehaviorSubject, Observable } from 'rxjs';
import { CONTAS_MOCK } from '../../mock/mock-data';

const LS_CHAVE = 'contas';

@Injectable({
  providedIn: 'root',
})
export class ContaService {

    private contasSubject: BehaviorSubject<Conta[]>;
    public contas$: Observable<Conta[]>
  
  
    constructor(){
      if(!localStorage[LS_CHAVE]){
  
        localStorage[LS_CHAVE] = JSON.stringify(CONTAS_MOCK); 
      }
      
      const contas: Conta[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
      this.contasSubject = new BehaviorSubject<Conta[]>(contas);
      this.contas$ = this.contasSubject.asObservable();
  
      console.log(`MOCK de contas inseridos, quantidade: ${contas.length}`);
    }
  
    private atualizarDados(contas: Conta[]){
      localStorage[LS_CHAVE] = JSON.stringify(contas);
      this.contasSubject.next(contas);
    }  

    atualizarConta(conta: Conta){

      try{
        const contas = this.listarTodos();
      
        const index = contas.findIndex(c => c.id === conta.id);
        if(index > -1){
          contas[index] = conta;
          this.atualizarDados(contas);
        }

        console.log("Conta atualizada com sucesso!");
      }catch(e){
        console.error("Erro ao atualizar conta");
      }

    }
  
    listarTodos() : Conta[]{
      return this.contasSubject.getValue();
    }

    buscarPorCpfCliente(cpf:string){
      const contas = this.listarTodos();

      return contas.find((conta) => conta.cpfCliente == cpf);
    }
  
  
}
