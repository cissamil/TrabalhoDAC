import { ContaCliente, GerenteCliente } from './ContaGerente';
import { Cliente } from './entities';

export interface ClienteConta {
  cliente: Cliente;
  conta: ContaCliente;
  gerente: GerenteCliente;
}
