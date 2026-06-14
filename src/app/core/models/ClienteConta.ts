import { ContaGerente } from './ContaGerente';
import { Cliente } from './entities';

export interface ClienteConta {
  cliente: Cliente;
  conta: ContaGerente;
}
