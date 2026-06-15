import { StatusConta } from './entities';

export interface GerenteCliente {
  nomeGerente: string;
}

export interface ContaCliente {
  contaId: string;
  saldo: number;
  limite: number;
  numeroConta: string;
}

export interface GerenteContasPendentes {
  contaId: string;
  clienteId: string;
  clienteCpf: string;
  clienteNome: string;
  clienteEmail: string;
  clienteSalario: number;
  statusConta?: StatusConta;
  motivoRecusa?: string;
}
