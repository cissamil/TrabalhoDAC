import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente, ClienteOutdated } from '../../models/entities';
import { PedidoAutoCadastroService } from '../pedido-autocadastro-services/pedido-autocadastro-service';
import { ContaService } from '../conta-services/conta-service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ClienteConta } from '../../models/ClienteConta';

//const LS_CHAVE = 'clientes';
const CLIENTE_CONTA_LOGADO = 'cliente_conta_logado';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  CLIENTE_URL="http://localhost:8080/clientes"

  httpOptions={
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    })
  }

  constructor(private httpClient: HttpClient){}

  listarTodos(token: string) : Observable<ClienteOutdated[]>{
    return this.httpClient.get<ClienteOutdated[]>(
      this.CLIENTE_URL,
      this.httpOptions
    );
  }

  inserir(cliente: Cliente): Observable<ClienteOutdated>{

    return this.httpClient.post<ClienteOutdated>(
      this.CLIENTE_URL + '/autocadastro' ,
      JSON.stringify(cliente),
      this.httpOptions
    );
  }

  atualizar(cliente: Cliente, token: string) : Observable<ClienteOutdated>{

    const header = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.httpClient.put<ClienteOutdated>(
      this.CLIENTE_URL + "/" + cliente.clienteId,
      JSON.stringify(cliente),
      {headers: header}
    );
  }

  remover(id: number) : Observable<ClienteOutdated>{

    return this.httpClient.delete<ClienteOutdated>(
      this.CLIENTE_URL + "/" + id,
      this.httpOptions
    )
  }

  buscarClientePorEmailESenha(email: string, senha: string): Observable<ClienteOutdated> {
  return this.httpClient.get<ClienteOutdated>(this.CLIENTE_URL + "/login", {
    ...this.httpOptions,
    params: { email, senha }
  });
}

  buscarPorCPF(cpf: string, token:string): Observable<ClienteOutdated> {
    return this.httpClient.get<ClienteOutdated>(
      this.CLIENTE_URL + "/cpf/" + cpf,
      this.httpOptions);
  }

  //---------- Métodos para gerenciamento de sessão do cliente logado
  public get clienteContaLogado(): ClienteConta | null {
    const clienteContaStr = localStorage.getItem(CLIENTE_CONTA_LOGADO);
    if (!clienteContaStr) return null;

    try {
      return JSON.parse(clienteContaStr) as ClienteConta;
    } catch (e) {
      console.error("Erro ao fazer parse de clienteContaLogado:", e);
      return null;
    }
  }

    // Salva o objeto transformando-o em String JSON
  public setClienteConta(clienteConta: ClienteConta): void {
    localStorage.setItem(CLIENTE_CONTA_LOGADO, JSON.stringify(clienteConta));
  }

  public clearClienteConta(): void {
    localStorage.removeItem(CLIENTE_CONTA_LOGADO);
  }
}
