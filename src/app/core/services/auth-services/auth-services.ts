import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
//import { Usuario } from '../../models/entities';

const AUTH_TOKEN_KEY = "auth_token";

@Injectable({
  providedIn: 'root',
})

export class AuthServices {

  AUTH_URL = "http://localhost:8080/auth"

  httpOptions={
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    })
  }
  constructor(private httpClient: HttpClient){}

  public get usuarioLogado(): string | null{
    const token = localStorage.getItem(AUTH_TOKEN_KEY)
    if (!token){
      return null;
    }
    return token;
  }

  login(userEmail:string, userPassword:string): Observable<{token:string}>{
    const credentals = {
      userEmail: userEmail,
      userPassword: userPassword
    }
    return this.httpClient.post<{token:string}>(
      `${this.AUTH_URL}/login`,
      credentals)
          .pipe(
          tap((response)=>{
          if (response && response.token){
            localStorage.setItem(AUTH_TOKEN_KEY, response.token);
          }
      }))
  }
}
