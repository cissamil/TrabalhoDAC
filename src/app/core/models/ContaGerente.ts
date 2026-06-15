import { StatusConta } from './entities';

export interface GerenteCliente {
  nomeGerente: string;
}

interface GerenteContaPendente {
  gerenteId:string;
  nome: string;
}

export interface ContaCliente {
  contaId: string;
  saldo: number;
  limite: number;
  numeroConta: string;
}

export interface GerenteContasPendentes {
  gerente:GerenteContaPendente;
  contas: ContaPendente[];

}

export interface ContaPendente{
  contaId: string;
  clienteId: string;
  clienteCpf: string;
  clienteNome: string;
  clienteEmail: string;
  clienteSalario: number;
}
