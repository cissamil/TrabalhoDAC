import { Endereco } from "./EnderecoEntity";

export interface MelhoresClientes {
    clienteId:string;
    cpf:string;
    nome:string;
    contaId: string;
    saldo: number;
    endereco:Endereco;
}
