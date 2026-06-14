import { GerenteContasPendentes } from "./ContaGerente";

export interface Cliente{
    id:number;
    cpf:string;
    nome:string;
    email:string;
    telefone:string;
    salario:number;
    endereco:string;
}

export interface GerenteAdmin{
    id:number;
    cpf:string;
    nome:string;
    email:string;
    telefone:string;
    tipo:string; // gerente / administrador
    //add pq une o perfil do gerente às suas contas vinculadas, mas de forma opcional
    //a interface sirve tanto no Login (dados simples) quanto no Dashboard (dados completos).
    contas?: GerenteContasPendentes[]
}

export enum StatusConta{
  CONTA_CRIADA = 'CONTA_CRIADA',
  CONTA_APROVADA =  'CONTA_APROVADA',
  CONTA_REJEITADA = 'CONTA_REJEITADA'
}

export interface Conta{
    id: number;
    saldo:number;
    limite:number;
    gerente:string;
    cliente: string;
    dataCriacao:Date;
    cpfGerente:string;
    cpfCliente:string;
    numeroConta: number;
    statusConta: StatusConta;
}


export interface ContaGerada {
  cpfCliente: string;
  numeroConta: string;
  limite: number;
  gerente: string;
  dataCriacao: Date;
}

export interface PedidoAutoCadastro {
  id: number,
  cpfCliente: string;
  nomeCliente: string;
  nomeGerente: string;
  cpfGerente: string;
  emailCliente:string;
  salario: number;
  dataSolicitacao: Date;
  status: "APROVADO" | "RECUSADO" | "PENDENTE";
  dataDecisao?: Date;
  motivoRecusa?: string;
  contaGerada?: Conta;
}

export interface EmailNotificacao {
  para: string;
  assunto: string;
  corpo: string;
  dataEnvio: Date;
}

export interface Movimentacao{
    id: number;
    data_hora: Date;
    tipo: "deposito" | "saque" | "transferencia"; // depósito/saque/transferência
    clienteOrigem: string;
    cpfClienteOrigem:string;
    clienteDestino: string;
    cpfClienteDestino:string;
    valor:number;
}

export interface Usuario{
  userId: string;
  email: string;
  tipoUsuario: TipoUsuario;
}

export enum TipoUsuario{
  CLIENTE,
  GERENTE,
  ADMIN
}
