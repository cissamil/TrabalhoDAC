import { Injectable } from '@angular/core';
import {Observable } from 'rxjs';
import { GerenteAdmin } from '../../models/entities';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const LS_CHAVE = 'gerentes';
const LS_CHAVE_LOGADO = 'gerenteLogado';

@Injectable({
  providedIn: 'root',
})
export class GerenteService {

  GERENTE_URL="http://localhost:8080";

  httpOptions={
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  //private gerentesSubject: BehaviorSubject<GerenteAdmin[]>;
  //onde os dados atuais ficam guardados
  //public gerentes$: Observable<GerenteAdmin[]>
  //versão que mostra as mudanças


  constructor(private httpClient: HttpClient){

    //this.gerentesSubject = new BehaviorSubject<GerenteAdmin[]>(gerentes);
    //cria o behavior com os dados do local
    //this.gerentes$ = this.gerentesSubject.asObservable();
    //deixa publico
  }


  listarTodos() : Observable<GerenteAdmin[]>{
    return this.httpClient.get<GerenteAdmin[]>(
      this.GERENTE_URL,
      this.httpOptions);
    //busca a lista atual no subject, retornando todos gerentes e adm
  }

  listarGerentes(): Observable<GerenteAdmin[]>{
    return this.httpClient.get<GerenteAdmin[]>(
      this.GERENTE_URL,
      this.httpOptions);
      //.filter((item) => item.tipo === 'gerente')
      //.sort((a,b) => a.nome.localeCompare(b.nome));
    }

    buscarPorId(id:number):Observable<GerenteAdmin>{
      return this.httpClient.get<GerenteAdmin>(
        this.GERENTE_URL + id,
        this.httpOptions);
    }

    inserir(gerente:GerenteAdmin): Observable<GerenteAdmin>{
      return this.httpClient.post<GerenteAdmin>(
      this.GERENTE_URL,
      JSON.stringify(gerente),
      this.httpOptions);
  }

  atualizar(gerente: GerenteAdmin) : Observable<GerenteAdmin> {
      return this.httpClient.put<GerenteAdmin>(
        this.GERENTE_URL + "/" + gerente.id,
        JSON.stringify(gerente),
        this.httpOptions);
    }


  remover(id:number): Observable<GerenteAdmin>{
    return this.httpClient.delete<GerenteAdmin>(
      this.GERENTE_URL + "/" + id,
      this.httpOptions);

    //const todosGerentes=this.listarGerentes();
    //if(todosGerentes.length<=1){
    //  alert("Não é permitido remover o último gerente.");
    //  return;
    //}

    //const novaListaGerentes=this.listarTodos().filter(g=>g.id !==idEmExclusao);
    //this.atualizarDados(novaListaGerentes);

  }


  // buscarGerentePorEmailESenhaETipo(email:string, senha:string, tipo:string){
  //   const gerentes = this.listarTodos();

  //   return gerentes.find(
  //     (gerente) =>
  //       gerente.email === email &&
  //       gerente.senha === senha &&
  //       gerente.tipo === tipo,
  //   );
  //   //busca o gerente que tenha o email, senha e tipo estritamente iguais
  // }



  //---------- Métodos para gerenciamento de sessão do gerente logado
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






}
