import { Injectable } from '@angular/core';
import { Cliente, Conta, Movimentacao } from '../models/entities';


const CLIENTE_ATIVO = "clienteAtivo";
const CONTA_ATIVA = "contaAtiva"; 
const MOVIMENTACOES_CLIENTE= "movimentacoes"

@Injectable({
  providedIn: 'root' 
})
export class ClienteSessionService {
  private _clienteAtivo: Cliente | null = null;
  private _contaCliente: Conta | null = null;
  // private _movimentacoesCliente: Movimentacao[] | null = null;

  constructor() {
    // Ao iniciar o serviço, verifica se tem dados salvos
    if (localStorage[CLIENTE_ATIVO]) {
      this._clienteAtivo = JSON.parse(localStorage[CLIENTE_ATIVO]);
    }
    if (localStorage[CONTA_ATIVA]) {
      this._contaCliente = JSON.parse(localStorage[CONTA_ATIVA]);
    }

    // if(localStorage[MOVIMENTACOES_CLIENTE]){
    //   this._movimentacoesCliente = JSON.parse(localStorage[MOVIMENTACOES_CLIENTE]);
    // }
  }
  
  private storeClienteData(){
    localStorage[CLIENTE_ATIVO] = JSON.stringify(this._clienteAtivo);
    localStorage[CONTA_ATIVA] = JSON.stringify(this._contaCliente);
    // localStorage[MOVIMENTACOES_CLIENTE] = JSON.stringify(this._movimentacoesCliente);
  }

  private removeClienteData(){
    localStorage.removeItem(CLIENTE_ATIVO) 
    localStorage.removeItem(CONTA_ATIVA);
    // localStorage.removeItem(MOVIMENTACOES_CLIENTE);
  }

  checkIfClienteIsLogged(): boolean{
    return !!localStorage[CLIENTE_ATIVO] && localStorage[CONTA_ATIVA] //&& localStorage[MOVIMENTACOES_CLIENTE];
  }

  setCliente(cliente: Cliente) {
    this._clienteAtivo = cliente;
    this.storeClienteData();
  }

  setContaCliente(conta: Conta){
    this._contaCliente = conta;
    this.storeClienteData();
  }

  // setMovimentacoesCliente(movimentacoes: Movimentacao[]){
  //   this._movimentacoesCliente = movimentacoes;
  //   this.storeClienteData();
  // }

  // getMovimentacoesCliente() : Movimentacao[] | null{
  //   return this._movimentacoesCliente;
  // }

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