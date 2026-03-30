import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { GerenteAdmin } from '../models/entities';
import { STAFF_MOCK } from '../mock/mock-data';

const LS_CHAVE = 'gerentes';
const LS_CHAVE_LOGADO = 'gerenteLogado';

@Injectable({
  providedIn: 'root',
})
export class GerenteService {


  private gerentesSubject: BehaviorSubject<GerenteAdmin[]>;
  //onde os dados atuais ficam guardados
  public gerentes$: Observable<GerenteAdmin[]>
  //versão que mostra as mudanças


  constructor(){
    if(!localStorage[LS_CHAVE]){
      //verifica se tem algo salvo

      localStorage[LS_CHAVE] = JSON.stringify(STAFF_MOCK);
      //se nao existir salva no mock
    }

    const gerentes: GerenteAdmin[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    //le o que está no localStorage
    this.gerentesSubject = new BehaviorSubject<GerenteAdmin[]>(gerentes);
    //cria o behavior com os dados do local
    this.gerentes$ = this.gerentesSubject.asObservable();
    //deixa publico

    console.log(`MOCK de gerentes inseridos, quantidade: ${gerentes.length}`);
  }

  private atualizarDados(gerentes: GerenteAdmin[]){
    //quando a lista mudar
    localStorage[LS_CHAVE] = JSON.stringify(gerentes);
    //salve no local
    this.gerentesSubject.next(gerentes);
    //e atualize o beahvior
  }

  listarTodos() : GerenteAdmin[]{
    return this.gerentesSubject.getValue();
    //busca a lista atual no subject, retornando todos gerentes e adm
  }

  inserir(gerente:GerenteAdmin): void{
    const gerentes = this.listarTodos();
    //pega a lista atual
    gerente.id = new Date().getTime();
    //gera um id
    gerentes.push(gerente);
    //add o novo gerente
    this.atualizarDados(gerentes);
    //salva
  }

  atualizar(gerente: GerenteAdmin) : void{
    const gerentes = this.listarTodos();

    const index = gerentes.findIndex((g) => g.id === gerente.id);
    if(index > -1){
      //procura o gerente pelo id
      gerentes[index] = gerente;
      //substitui pelo atualizado
      this.atualizarDados(gerentes);
      //salva
    }
  }

  remover(id: number) : void{
    const gerentes = this.listarTodos()
    //lista os gerentes
      .filter((g) => g.id !== id);
      //busca apenas aquele id e remove

    this.atualizarDados(gerentes);
    //atualiza
  }

  buscarGerentePorEmailESenhaETipo(email:string, senha:string, tipo:string){
    const gerentes = this.listarTodos();

    return gerentes.find(
      (gerente) =>
        gerente.email === email &&
        gerente.senha === senha &&
        gerente.tipo === tipo,
    );
    //busca o gerente que tenha o email, senha e tipo estritamente iguais
  }

  // Métodos para gerenciamento de sessão do gerente logado
  setGerenteLogado(gerente: GerenteAdmin): void {
    localStorage[LS_CHAVE_LOGADO] = JSON.stringify(gerente);
    //salva o usuario logado
  }

  getGerenteLogado(): GerenteAdmin | null {
    const gerenteData = localStorage[LS_CHAVE_LOGADO];
    if (!gerenteData) {
      return null;
    }
    return JSON.parse(gerenteData) as GerenteAdmin;
    //recupera o usuario logado
  }

  isGerenteLogado(): boolean {
    return !!localStorage[LS_CHAVE_LOGADO];
    //verifica se tem alguem logado
  }

  logout(): void {
    localStorage.removeItem(LS_CHAVE_LOGADO);
    //faz logout
  }

  listarGerentes():GerenteAdmin[] {
    return this.gerentesSubject
      .getValue()
      .filter((item)=> item.tipo==='gerente')
      .sort((a,b)=>a.nome.localeCompare(b.nome));

  }
}
