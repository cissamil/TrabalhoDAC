import { StatusConta } from './entities';

export interface GerenteNome {
  nomeGerente: string;
}

export interface ContaGerente {
  contaId: string;
  saldo: number;
  limite: number;
  numeroConta: string;
  gerente: GerenteNome;
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
