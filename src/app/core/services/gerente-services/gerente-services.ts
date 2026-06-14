import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable } from 'rxjs';
import { GerenteAdmin } from '../../models/entities';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DashboardGerenciarGerentes } from '../../../pages/admin/adm-gerenciar-gerentes/adm-gerenciar-gerentes';

const GERENTE_LOGADO = 'gerenteLogado';
const ADMIN_LOGADO = 'adminLogado';
@Injectable({
  providedIn: 'root',
})
export class GerenteService {

  GERENTE_URL="http://localhost:8080/gerentes";

  httpOptions={
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient){

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
        this.GERENTE_URL + "/" + id,
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

  //   return []
  //   // gerentes.find(
  //   //   (gerente) =>
  //   //     gerente.email === email &&
  //   //     gerente.senha === senha &&
  //   //     gerente.tipo === tipo,
  //   // ) ;
  //   //busca o gerente que tenha o email, senha e tipo estritamente iguais
  // }

buscarGerentePorEmailESenhaETipo(email: string, senha: string, tipo:string): Observable<GerenteAdmin> {
  return this.httpClient.get<GerenteAdmin>(this.GERENTE_URL + "/login", {
    ...this.httpOptions,
    params: {
      email,
      senha,
      tipo}
  });
}

obterDashboardGerenciarGerentes(): Observable<DashboardGerenciarGerentes> {
  return this.httpClient.get<DashboardGerenciarGerentes>(
    this.GERENTE_URL + '/dashboard/',
    this.httpOptions
  );
}

  //---------- Métodos para gerenciamento de sessão do gerente logado
  public setGerente(gerente: GerenteAdmin): void {
        localStorage.setItem(GERENTE_LOGADO, JSON.stringify(gerente));
      }

  public get GerenteLogado(): GerenteAdmin | null{
    const gerenteStr = localStorage.getItem(GERENTE_LOGADO);
    if (!gerenteStr) return null;

    try {
      return JSON.parse(gerenteStr) as GerenteAdmin;

    } catch (e) {
      console.error("Erro ao fazer parse de GerenteLogado:", e);
      return null;

    }
  }

  public clearGerente(): void{
      localStorage.removeItem(GERENTE_LOGADO);
    }

  isGerenteLogado(): boolean {
    return !!localStorage[GERENTE_LOGADO];
    //verifica se tem alguem logado
  }

  logoutGerente(): void {
    localStorage.removeItem(GERENTE_LOGADO);
    //faz logout
  }



  //separados pq mesmo sendo a mesma entidade, eles possuem telas e dados diferentes,
  //usar a mesma chave causa sobreescrição e pode travar o sistema e as infos
  //---------- Métodos para gerenciamento de sessão do admin logado
  public setAdmin(admin: GerenteAdmin): void {
        localStorage.setItem(ADMIN_LOGADO, JSON.stringify(admin));
      }

  public get AdminLogado(): GerenteAdmin | null{
    const adminStr = localStorage.getItem(ADMIN_LOGADO);
    if (!adminStr) return null;

    try {
      return JSON.parse(adminStr) as GerenteAdmin;

    } catch (e) {
      console.error("Erro ao fazer parse de GerenteLogado:", e);
      return null;

    }
  }

  public clearAdmin(): void{
      localStorage.removeItem(ADMIN_LOGADO);
    }

  isAdminLogado(): boolean {
    return !!localStorage[ADMIN_LOGADO];
    //verifica se tem alguem logado
  }

  logoutAdmin(): void {
    localStorage.removeItem(ADMIN_LOGADO);
    //faz logout
  }





}
