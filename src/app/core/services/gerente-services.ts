import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { GerenteAdmin } from '../models/entities';
import { STAFF_MOCK } from '../mock/mock-data';

const LS_CHAVE = 'gerentes';

@Injectable({
  providedIn: 'root',
})
export class GerenteService {


  private gerentesSubject: BehaviorSubject<GerenteAdmin[]>;
  public gerentes$: Observable<GerenteAdmin[]>


  constructor(){
    if(!localStorage[LS_CHAVE]){

      localStorage[LS_CHAVE] = JSON.stringify(STAFF_MOCK); 
    }
    
    const gerentes: GerenteAdmin[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    this.gerentesSubject = new BehaviorSubject<GerenteAdmin[]>(gerentes);
    this.gerentes$ = this.gerentesSubject.asObservable();

    console.log(`MOCK de gerentes inseridos, quantidade: ${gerentes.length}`);
  }

  private atualizarDados(gerentes: GerenteAdmin[]){
    localStorage[LS_CHAVE] = JSON.stringify(gerentes);
    this.gerentesSubject.next(gerentes);
  }  

  listarTodos() : GerenteAdmin[]{
    return this.gerentesSubject.getValue();
  }

  inserir(gerente:GerenteAdmin): void{
    const gerentes = this.listarTodos();
    gerente.id = new Date().getTime();
    gerentes.push(gerente);
    this.atualizarDados(gerentes);
  }

  atualizar(gerente: GerenteAdmin) : void{
    const gerentes = this.listarTodos();
    
    const index = gerentes.findIndex((g) => g.id === gerente.id);
    if(index > -1){
      gerentes[index] = gerente;
      this.atualizarDados(gerentes);
    }
  }

  remover(id: number) : void{
    const gerentes = this.listarTodos()
      .filter((g) => g.id !== id);

    this.atualizarDados(gerentes);
  }

  buscarGerentePorEmailESenhaETipo(email:string, senha:string, tipo:string){
    const gerentes = this.listarTodos();

    return gerentes.find(
      (gerente) =>
        gerente.email === email &&
        gerente.senha === senha &&
        gerente.tipo === tipo,
    );
  }
  
}
