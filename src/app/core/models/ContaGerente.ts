import { StatusConta } from "./entities";

export interface GerenteNome{
  nomeGerente:string;
}

export interface ContaGerente{
    id: number;
    saldo:number;
    limite:number;
    gerente:GerenteNome;
    cliente: string;
    dataCriacao:Date;
    cpfGerente:string;
    cpfCliente:string;
    numeroConta: number;
    salario: number;
}

export interface GerenteContasPendentes {
  contaId: string
  clienteId: string;
  clienteCpf: string
  clienteNome: string
  clienteEmail: string
  clienteSalario: number
  statusConta?: StatusConta;
  motivoRecusa?: string;
}
