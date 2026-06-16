export interface MovimentacaoCliente{
    dataHora:Date;
    valor:number;
    movimentacaoId:string;
    tipoMovimentacao:string;
    clienteDestinoId:string;
    clienteOrigemNome:string;
    clienteDestinoNome:string;
    clienteOrigemId:string;
}