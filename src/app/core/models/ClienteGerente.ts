import { Endereco } from "./EnderecoEntity";

export interface ClienteGerente {
    clienteId:string;
    cpf:string;
    nome:string;
    endereco:Endereco;
    salario:number;
    contaId: string;
    saldo: number;
    limite: number;
    email:string;
    telefone:string;
    numeroConta:string;
}
