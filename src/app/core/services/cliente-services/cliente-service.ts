import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cliente, GerenteAdmin, PedidoAutoCadastro } from '../../models/entities';
import { CLIENTES_MOCK } from '../../mock/mock-data';
import { PedidoAutoCadastroService } from '../pedido-autocadastro-services/pedido-autocadastro-service';
import { ContaService } from '../conta-services/conta-service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const LS_CHAVE = 'clientes';

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

  // private clientesSubject: BehaviorSubject<Cliente[]>;
  // public clientes$: Observable<Cliente[]>

  constructor(private httpClient: HttpClient){}
  // constructor(private pedidoAutoCadastroService: PedidoAutoCadastroService, private contaService: ContaService){
  //   if(!localStorage[LS_CHAVE]){

  //     localStorage[LS_CHAVE] = JSON.stringify(CLIENTES_MOCK);
  //   }

    // localStorage[LS_CHAVE] = JSON.stringify(CLIENTES_MOCK);
    //const clientes: Cliente[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    //this.clientesSubject = new BehaviorSubject<Cliente[]>(clientes);
    //this.clientes$ = this.clientesSubject.asObservable();

    //console.log(`MOCK de clientes inseridos, quantidade: ${clientes.length}`);
  //}

  private atualizarDados(clientes: Cliente[]){
    return this.httpClient.post<Cliente>(
    this.CLIENTE_URL,
    JSON.stringify(clientes),
    this.httpOptions
  );
  }

  listarTodos() : Observable<Cliente[]>{
    return this.httpClient.get<Cliente[]>(
      this.CLIENTE_URL,
      this.httpOptions
    );
  }

  inserir(cliente:Cliente): Observable<Cliente>{

    return this.httpClient.post<Cliente>(
      this.CLIENTE_URL,
      JSON.stringify(cliente),
      this.httpOptions
    );
    // console.log(`
    //   Cliente a inserir:
    //   Nome: ${cliente.nome}
    //   Email: ${cliente.email}
    //   Senha: ${cliente.senha}
    //   Salario: ${cliente.salario}
    //   Endereço: ${cliente.endereco}
    // `);

    // try{

    //   const clientes = this.listarTodos();
    //   cliente.id = new Date().getTime();
    //   clientes.push(cliente);
    //   this.atualizarDados(clientes);

    //   const gerente = this.contaService.buscarGerenteComMenosClientes();

    //   this.enviarSolicitacaoDeConta(cliente, gerente);

    //   console.log("Gerente com menos clientes:", gerente.nome);

    // }catch(e){

    //   console.error("Erro ao inserir usuário: ", e)

    // }

  }

  // private enviarSolicitacaoDeConta(cliente:Cliente, gerente: GerenteAdmin){

  //   const pedidoCadastro: PedidoAutoCadastro ={
  //     id: 0,
  //     nomeCliente: cliente.nome,
  //     nomeGerente: gerente.nome,
  //     cpfCliente: cliente.cpf,
  //     cpfGerente: gerente.cpf,
  //     emailCliente: cliente.email,
  //     salario: cliente.salario,
  //     dataSolicitacao: new Date(),
  //     status: 'PENDENTE'
  //   }

  //   console.log("[SERVICE] Pedido a ser enviado:", pedidoCadastro);

  //   this.pedidoAutoCadastroService.inserir(pedidoCadastro);
  // }

  atualizar(cliente: Cliente) : Observable<Cliente>{
    // console.log(`
    //   Dados a atualizar:
    //   Id: ${cliente.id}
    //   Nome: ${cliente.nome}
    //   Email: ${cliente.email}
    //   Senha: ${cliente.senha}
    //   Salario: ${cliente.salario}
    //   Endereço: ${cliente.endereco}
    // `);
    //const clientes = this.listarTodos();

    // const index = clientes.findIndex(c => c.id === cliente.id);
    // if(index > -1){
    //   clientes[index] = cliente;
    //   this.atualizarDados(clientes);
    // }
    return this.httpClient.put<Cliente>(
      this.CLIENTE_URL + "/" + cliente.id,
      JSON.stringify(cliente),
      this.httpOptions
    );
  }

  // buscarPorCPF(cpf: string){
  //   const clientes = this.listarTodos();

  //   return clientes.find((cliente) => cliente.cpf === cpf);
  // }

  remover(id: number) : Observable<Cliente>{
    // const clientes = this.listarTodos()
    //   .filter((c) => c.id !== id);

    // this.atualizarDados(clientes);

    return this.httpClient.delete<Cliente>(
      this.CLIENTE_URL + "/" + id,
      this.httpOptions
    )
  }

  // buscarClientePorEmail(email:string){
  //   const clientes = this.listarTodos();

  //   return clientes.find((cliente) => cliente.email === email);
  // }

  // buscarClientePorCPF(cpf:string){
  //   const clientes = this.listarTodos();

  //   // console.log("Clientes: ", clientes);
  //   return clientes.find((cliente) => cliente.cpf === cpf);
  // }

  // buscarClientePorEmailESenha(email:string, senha:string){
  //   const clientes = this.listarTodos();
  //   return clientes.find((cliente) => cliente.email === email && cliente.senha === senha);
  // }


}
