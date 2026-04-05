import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CONTAS_MOCK } from '../../mock/mock-data';
import { GerenteService } from '../gerente-services';
import { ClienteSessionService } from '../session-controller.service';
import { Conta, GerenteAdmin, Movimentacao } from '../../models/entities';
import { MovimentacaoService } from '../movimentacoes-service/movimentacao-service';
import { ClienteService } from '../cliente-services/cliente-service';

const LS_CHAVE = 'contas';

@Injectable({
  providedIn: 'root',
})
export class ContaService {

  private contasSubject: BehaviorSubject<Conta[]>;
  public contas$: Observable<Conta[]>


  constructor(private clienteSessionService: ClienteSessionService, private movimentacaoService: MovimentacaoService){
    if(!localStorage[LS_CHAVE]){

      localStorage[LS_CHAVE] = JSON.stringify(CONTAS_MOCK); 
    }

    // localStorage[LS_CHAVE] = JSON.stringify(CONTAS_MOCK); 

    
    const contas: Conta[]= localStorage[LS_CHAVE] ? JSON.parse(localStorage[LS_CHAVE]) : [];
    this.contasSubject = new BehaviorSubject<Conta[]>(contas);
    this.contas$ = this.contasSubject.asObservable();

    console.log(`MOCK de contas inseridos, quantidade: ${contas.length}`);
  }

  private atualizarDados(contas: Conta[]){
    localStorage[LS_CHAVE] = JSON.stringify(contas);
    this.contasSubject.next(contas);
  }  

  inserir(conta: Conta): void{
    try{

      const contas = this.listarTodos();
      conta.id = new Date().getTime();
      contas.push(conta);
      this.atualizarDados(contas);

    }catch(e){

      console.error("Erro ao inserir usuário: ", e)

    }

  }
  

  atualizarConta(conta: Conta){

    try{
      const contas = this.listarTodos();
    
      const index = contas.findIndex(c => c.id === conta.id);
      if(index > -1){
        contas[index] = conta;
        this.atualizarDados(contas);
      }

      console.log("Conta atualizada com sucesso!");
    }catch(e){
      console.error("Erro ao atualizar conta ", e);
    }

  }

  realizarTransferencia(contaOrigem: Conta, contaDestino: Conta, valor:number){
    try{

      const contas = this.listarTodos();
      
      this.listarConta(contaDestino.numeroConta);
      const indexOrigem = contas.findIndex(c => c.id === contaOrigem.id);
      const indexDestino = contas.findIndex(c => c.id === contaDestino.id);
      
      if(indexOrigem > -1 && indexDestino > -1){
        contas[indexOrigem] = contaOrigem;
        contas[indexDestino] = contaDestino;
        this.atualizarDados(contas);

        this.registrarMovimentacao(valor, contaOrigem, contaDestino)
        this.clienteSessionService.setContaCliente(contaOrigem);
      }

      this.listarConta(contaDestino.numeroConta);

    }catch(e){
      console.error("Erro ao atualizar conta ", e);
    }
  }

  registrarMovimentacao(valor: number, contaOrigem: Conta, contaDestino:Conta){

  


    const movimentacao: Movimentacao = {
      id:0,
      valor: valor,
      data_hora: new Date(),
      tipo:'transferencia',
      clienteOrigem: contaOrigem.cliente,
      clienteDestino: contaDestino.cliente,
      cpfClienteDestino: contaDestino.cpfCliente,
      cpfClienteOrigem: contaOrigem.cpfCliente
    }

    this.movimentacaoService.inserir(movimentacao);    

  }


  buscarGerenteComMenosClientes(): GerenteAdmin{

    const gerenteService: GerenteService = new GerenteService();
    const contas = this.listarTodos();
    const gerentes = gerenteService.listarGerentes();
    
    const contagemPorGerente = gerentes.map((gerente) =>{
      return {
        dados: gerente,
        totalClientes: contas.filter((c) => c.cpfGerente === gerente.cpf).length
      };
    });

    contagemPorGerente.sort((a, b) => a.totalClientes - b.totalClientes);

    console.log("Contagem: ", contagemPorGerente);

    return contagemPorGerente[0].dados;

  }

  listarConta(numeroConta:number){

    const contas = this.listarTodos();

    const conta = contas.find((conta) => conta.numeroConta === numeroConta);

    console.log("Valor atual conta: ", conta?.saldo);

  }

  listarTodos() : Conta[]{
    return this.contasSubject.getValue();
  }

  buscarPorCpfCliente(cpf:string){
    const contas = this.listarTodos();

    return contas.find((conta) => conta.cpfCliente == cpf);
  }
  
  buscarPorNumeroConta(numero: string){

    const numeroConta = Number(numero);

    const contas = this.listarTodos();

    return contas.find((conta) => conta.numeroConta === numeroConta);
  }
  
  
}
