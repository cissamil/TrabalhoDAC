import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClienteConta } from '../../models/ClienteConta';

@Injectable({
  providedIn: 'root',
})
export class CompositionServices {
  COMPOSITION_URL="http://localhost:8080"

  constructor(private httpClient: HttpClient){}

//   httpOptions={
//     headers: new HttpHeaders({
//       'Content-type': 'application/json',
//     })
//   }


//   getClienteConta(token: string): Observable<ClienteConta>{
//     return this.httpClient.get<ClienteConta>(
//       `${this.COMPOSITION_URL}/cliente-conta`,
//       this.httpOptions)
//   }
// }

getClienteConta(token: string): Observable<ClienteConta> {
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
}
