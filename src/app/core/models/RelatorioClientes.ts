export interface ClienteRelatorio{
    cliente: Cliente;
    conta: Conta;
    gerente:Gerente;
}

interface Cliente{
    nome:string;
    cpf:string;
    email:string;
    clienteId:string;
    salario:number;
}

interface Conta{

    contaId:string;
    saldo:number;
    limite:number;
    numeroConta:string;
}

interface Gerente{
    nome:string;
    cpf:string;
    gerenteId:string;
}