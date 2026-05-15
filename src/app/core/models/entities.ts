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
    cpfGerente:string;
    cpfCliente:string;
    numeroConta: number;
}



export interface ContaGerada {
  cpfCliente: string;
  numeroConta: string;
  senha: string;
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
