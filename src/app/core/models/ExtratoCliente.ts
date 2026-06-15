import { MovimentacaoCliente } from "./MovimentacaoCliente";

export interface ExtratoCliente{
    clienteId:string;
    movimentacoes:MovimentacaoCliente[];
}

