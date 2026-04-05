import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cliente } from '../../models/entities';
import { CLIENTES_MOCK } from '../../mock/mock-data';

const LS_CHAVE = 'clientes';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private clientesSubject: BehaviorSubject<Cliente[]>;
  public clientes$: Observable<Cliente[]>


  constructor(){
    if(!localStorage[LS_CHAVE]){

      localStorage[LS_CHAVE] = JSON.stringify(CLIENTES_MOCK);
    }

    const clientes: Cliente[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    this.clientesSubject = new BehaviorSubject<Cliente[]>(clientes);
    this.clientes$ = this.clientesSubject.asObservable();

    console.log(`MOCK de clientes inseridos, quantidade: ${clientes.length}`);
  }

  private atualizarDados(clientes: Cliente[]){
    localStorage[LS_CHAVE] = JSON.stringify(clientes);
    this.clientesSubject.next(clientes);
  }

  listarTodos() : Cliente[]{
    return this.clientesSubject.getValue();
  }

  inserir(cliente:Cliente): void{

    console.log(`
      Cliente a inserir:
      Nome: ${cliente.nome}
      Email: ${cliente.email}
      Senha: ${cliente.senha}
      Salario: ${cliente.salario}
      Endereço: ${cliente.endereco}
    `);

    const clientes = this.listarTodos();
    cliente.id = new Date().getTime();
    clientes.push(cliente);
    this.atualizarDados(clientes);
  }

  atualizar(cliente: Cliente) : void{
    console.log(`
      Dados a atualizar:
      Id: ${cliente.id}
      Nome: ${cliente.nome}
      Email: ${cliente.email}
      Senha: ${cliente.senha}
      Salario: ${cliente.salario}
      Endereço: ${cliente.endereco}
    `);
    const clientes = this.listarTodos();

    const index = clientes.findIndex(c => c.id === cliente.id);
    if(index > -1){
      clientes[index] = cliente;
      this.atualizarDados(clientes);
    }
  }

  buscarPorCPF(cpf: string){
    const clientes = this.listarTodos();

    return clientes.find((cliente) => cliente.cpf === cpf);
  }

  remover(id: number) : void{
    const clientes = this.listarTodos()
      .filter((c) => c.id !== id);

    this.atualizarDados(clientes);
  }

  buscarClientePorEmail(email:string){
    const clientes = this.listarTodos();

    return clientes.find((cliente) => cliente.email === email);
  }

  buscarClientePorEmailESenha(email:string, senha:string){
    const clientes = this.listarTodos();

    return clientes.find((cliente) => cliente.email === email && cliente.senha === senha);
  }

 
}
