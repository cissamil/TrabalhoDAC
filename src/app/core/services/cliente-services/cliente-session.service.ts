import { Injectable } from '@angular/core';
import { Cliente, Conta } from '../../models/entities';
import { CONTAS_MOCK } from '../../mock/mock-data';

@Injectable({
  providedIn: 'root' 
})
export class ClienteSessionService {
  private _clienteAtivo: Cliente | null = null;
  private _contaCliente: Conta | null = null;

  constructor() {
    // Ao iniciar o serviço, verifica se tem dados salvos
    if (localStorage['clienteAtivo']) {
      this._clienteAtivo = JSON.parse(localStorage['clienteAtivo']);
    }
    if (localStorage['contaAtiva']) {
      this._contaCliente = JSON.parse(localStorage['contaAtiva']);
    }
  }
  
  private storeClienteData(){
    localStorage['clienteAtivo'] = JSON.stringify(this._clienteAtivo);
    localStorage['contaAtiva'] = JSON.stringify(this._contaCliente);
  }

  private removeClienteData(){
    localStorage.removeItem('clienteAtivo') 
    localStorage.removeItem('contaAtiva');
  }

  checkIfClienteIsLogged(): boolean{
    return !!localStorage['clienteAtivo'] && localStorage['contaAtiva'];
  }

  setCliente(cliente: Cliente) {
    this._clienteAtivo = cliente;
    this.storeClienteData();
  }

  setContaCliente(conta: Conta){
    this._contaCliente = conta;
    this.storeClienteData();
  }

  getCliente(): Cliente | null {
    return this._clienteAtivo;
  }

  getConta(): Conta | null{
    return this._contaCliente;
  }

  clearSession() {
    this._clienteAtivo = null;
    this._contaCliente = null;
    this.removeClienteData();
  }
}