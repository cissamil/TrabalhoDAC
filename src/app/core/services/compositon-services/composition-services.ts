import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClienteConta } from '../../models/ClienteConta';
import { GerenteAdmin } from '../../models/entities';

@Injectable({
  providedIn: 'root',
})
export class CompositionServices {
  COMPOSITION_URL="http://localhost:8080"

  constructor(private httpClient: HttpClient){}


public getClienteConta(token: string): Observable<ClienteConta> {
  const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // Garante que o gateway/microserviço receba a autenticação
      })
    };

    // Correção da sintaxe do GET utilizando o HttpClient
    return this.httpClient.get<ClienteConta>(
      `${this.COMPOSITION_URL}/cliente-conta`,
      httpOptions
    );
  }

  public getGerente(token: string): Observable<GerenteAdmin> {
  const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // Garante que o gateway/microserviço receba a autenticação
      })
    };

    // puxa o gerente
    return this.httpClient.get<GerenteAdmin>(
      `${this.COMPOSITION_URL}/gerente`,
      httpOptions
    );
  }

  public getAdmin(token: string): Observable<GerenteAdmin> {
  const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // Garante que o gateway/microserviço receba a autenticação
      })
    };

    // puxa o adm
    return this.httpClient.get<GerenteAdmin>(
      `${this.COMPOSITION_URL} /dashboard-admin`,
      httpOptions
    );
  }

  public getContasPendentes(token: string): Observable<ClienteConta[]>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // Garante que o gateway/microserviço receba a autenticação
      })
    }

    return this.httpClient.get<ClienteConta[]>(
      `${this.COMPOSITION_URL}/gerente/contas-pendentes`,
      httpOptions
    )
  }
}
