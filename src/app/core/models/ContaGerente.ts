export interface GerenteNome{
  nomeGerente:string;
}

export interface ContaGerente{
    id: number;
    saldo:number;
    limite:number;
    gerente:GerenteNome;
    cliente: string;
    dataCriacao:Date;
    cpfGerente:string;
    cpfCliente:string;
    numeroConta: number;
}
