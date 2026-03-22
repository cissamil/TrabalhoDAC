export interface Cliente{
    cpf:string;
    nome:string;
    email:string;
    senha:string;
    salario:number;
    endereco:string;
}

export interface GerenteAdmin{
    cpf:string;
    nome:string;
    email:string;
    senha:string;
    tipo:string; // gerente / administrador
}

export interface Conta{
    cliente: string;
    numeroConta: number;
    saldo:number;
    limite:number;
    gerente:string;
    dataCriacao:Date;
}

export interface Movimentacao{
    data_hora: Date;
    tipo:string; // depósito/saque/transferência
    clienteOrigem: string;
    clienteDestino: string;
    valor:number;
}