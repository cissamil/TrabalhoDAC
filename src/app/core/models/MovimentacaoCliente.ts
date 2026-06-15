export interface MovimentacaoCliente{
    dataHora:Date;
    valor:number;
    movimentacaoId:string;
    clienteDestinoNome:string;
    tipoMovimentacao:string;
}