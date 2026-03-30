export interface Cliente{
    id:number;
    cpf:string;
    nome:string;
    email:string;
    telefone:string;
    senha:string;
    salario:number;
    endereco:string;
}

export interface GerenteAdmin{
    id:number;
    cpf:string;
    nome:string;
    email:string;
    telefone:string;
    senha:string;
    tipo:string; // gerente / administrador
}

export interface Conta{
    id: number;
    saldo:number;
    limite:number;
    gerente:string;
    cliente: string;
    dataCriacao:Date;
    cpfCliente:string;
    numeroConta: number;
}

export interface Movimentacao{
    id: number;
    data_hora: Date;
    tipo:string; // depósito/saque/transferência
    clienteOrigem: string;
    clienteDestino: string;
    valor:number;
}
