import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardGerenciarGerentes } from '../../../pages/admin/adm-gerenciar-gerentes/adm-gerenciar-gerentes';
import { Gerente } from '../../models/entities';
import { AdicionarGerente } from '../../models/AdicionarGerente';

const GERENTE_LOGADO = 'gerenteLogado';
const ADMIN_LOGADO = 'adminLogado';
@Injectable({
  providedIn: 'root',
})
export class GerenteService {
  GERENTE_URL = 'http://localhost:8080/gerentes';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private httpClient: HttpClient) {}

  listarGerentes(token: string): Observable<Gerente[]> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.get<Gerente[]>(this.GERENTE_URL, {
      headers: header,
    });
    //busca a lista atual no subject, retornando todos gerentes e adm
  }

  buscarPorId(id: number): Observable<Gerente> {
    return this.httpClient.get<Gerente>(
      this.GERENTE_URL + '/' + id,
      this.httpOptions,
    );
  }

  inserir(gerente: AdicionarGerente, token:string): Observable<Gerente> {

    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.post<Gerente>(
      this.GERENTE_URL,
      JSON.stringify(gerente),
      {headers: header}
    );
  }

  atualizar(id: string, gerente: AdicionarGerente, token: string): Observable<Gerente> {

    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.put<Gerente>(
      this.GERENTE_URL + '/' + id,
      JSON.stringify(gerente),
      {headers: header}
    );
  }

  remover(id:string, token:string): Observable<Gerente> {
    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });
    
    return this.httpClient.delete<Gerente>(
      this.GERENTE_URL + '/' + id,
      {headers: header}
    );
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

  buscarGerentePorEmailESenhaETipo(
    email: string,
    senha: string,
    tipo: string,
  ): Observable<Gerente> {
    return this.httpClient.get<Gerente>(this.GERENTE_URL + '/login', {
      ...this.httpOptions,
      params: {
        email,
        senha,
        tipo,
      },
    });
  }

  //---------- Métodos para gerenciamento de sessão do gerente logado
  public setGerente(gerente: Gerente): void {
    localStorage.setItem(GERENTE_LOGADO, JSON.stringify(gerente));
  }

  public get GerenteLogado(): Gerente | null {
    const gerenteStr = localStorage.getItem(GERENTE_LOGADO);
    if (!gerenteStr) return null;

    try {
      return JSON.parse(gerenteStr) as Gerente;
    } catch (e) {
      console.error('Erro ao fazer parse de GerenteLogado:', e);
      return null;
    }
  }

  public clearGerente(): void {
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
  public setAdmin(admin: Gerente): void {
    localStorage.setItem(ADMIN_LOGADO, JSON.stringify(admin));
  }

  public get AdminLogado(): Gerente | null {
    const adminStr = localStorage.getItem(ADMIN_LOGADO);
    if (!adminStr) return null;

    try {
      return JSON.parse(adminStr) as Gerente;
    } catch (e) {
      console.error('Erro ao fazer parse de GerenteLogado:', e);
      return null;
    }
  }

  public clearAdmin(): void {
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
