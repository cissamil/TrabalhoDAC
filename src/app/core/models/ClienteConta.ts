
import {ContaGerente} from "./ContaGerente"

export interface ClienteConta{

    id:number;
    cpf:string;
    nome:string;
    email:string;
    telefone:string;
    salario:number;
    endereco:string;
    conta: ContaGerente;

}
