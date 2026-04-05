export interface ManagerTableData{
    nome: string;
    cpf: string;
    quantidadeClientes:number;
    saldosPositivos:number;
    saldosNegativos:number;
    saldoTotal:number;
    colorSaldoTotal:string;
}

export interface ClientTableData{
    cpfCliente:string;
    nomeCliente:string;
    emailCliente:string;
    salarioCliente:number;
    numeroContaCliente:number;
    saldoContaCliente:number;
    limiteContaCliente:number;
    cpfGerente:string;
    nomeGerente:string;
    colorSaldo:string;
}

export interface ManagerListTableData {
    id:number;
    nome: string;
    cpf: string;
    email: string;
    telefone: string;
    quantidadeClientes: number;
}
