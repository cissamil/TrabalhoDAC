package br.ufpr.common.constants;

public final class RabbitMQConstants {

  // Exchanges
  public static final String BANTADS_EXCHANGE = "bantads.exchange";

  // ROUTING KEYS

  // CLIENTE
  public static final String RK_CLIENTE_CRIADO_SUCESSO = "cliente.criado.sucesso.evento";
  public static final String RK_CLIENTE_DELETAR_COMANDO = "cliente.deletar.comando";

  //EMAIL
  public static final String RK_EMAIL_ENVIADO_COM_SUCESSO = "email.enviado.sucesso.evento";
  public static final String RK_EMAIL_ENVIAR_COMANDO = "email.enviar.comando";

  // CONTA
  public static final String RK_CONTA_CRIAR_COMANDO = "conta.criar.comando";
  public static final String RK_CONTA_CRIADA_FALHA = "conta.criada.falha.evento";
  public static final String RK_CONTA_APROVADA_SUCESSO = "conta.aprovada.sucesso";
  public static final String RK_CONTA_REVERTER_STATUS_COMANDO = "conta.reverter.status.comando";


  public static final String RK_CONTAS_TRANSFERIR_COMANDO = "contas.transferir.comando";
  public static final String RK_CONTAS_TRANSFERIDAS_SUCESSO = "contas.transferidas.sucesso";
  public static final String RK_CREDENCIAL_GERENTE_DELETADA_EVENTO = "credencial.gerente.deletada.evento";
  public static final String RK_GERENTE_REMOVER_EVENTO = "gerente.remover.evento";
  public static final String RK_GERENTE_REMOVER_COMANDO = "gerente.remover.comando";
  public static final String RK_GERENTE_CREDENCIAL_REMOVER_COMANDO = "gerente.credencial.remover.comando";

  public static final String RK_GERENTE_CRIADO_EVENTO = "gerente.criado.evento";
  public static final String RK_CONTA_ATRIBUIR_COMANDO = "conta.atribuir.comando";
  public static final String RK_CONTA_ATRIBUIR_FALHA_EVENTO = "conta.atribuir.falha.evento";

  // AUTH
  public static final String RK_CLIENTE_CREDENCIAL_CRIAR_COMANDO = "cliente.credencial.criar.comando";
  public static final String RK_CLIENTE_CREDENCIAL_GERADA_FALHA = "cliente.credencial.falha.evento";

  public static final String RK_GERENTE_CREDENCIAL_CRIAR_COMANDO = "gerente.credencial.criar.comando";
  public static final String RK_GERENTE_CREDENCIAL_GERADA_EVENTO = "gerente.credencial.gerada.evento";
  public static final String RK_GERENTE_CREDENCIAL_DELETADA_EVENTO = "gerente.credencial.evento.evento";
  public static final String RK_GERENTE_CREDENCIAL_GERADA_FALHA = "gerente.credencial.falha.evento";

  // QUEUES
  public static final String FILA_SAGA_CLIENTE_CRIADO = "ms-saga.cliente.criado.queue";
  public static final String FILA_SAGA_CONTA_CRIADA_FALHA = "ms-saga.conta.criada.falha.queue";
  public static final String FILA_SAGA_CONTA_APROVADA = "ms-saga.conta.aprovada.queue";
  public static final String FILA_SAGA_CONTA_REPROVADA = "ms-saga.conta.reprovada.queue";
  public static final String FILA_SAGA_GERENTE_REMOVER = "ms-saga.gerente.remover.queue";
  public static final String FILA_GERENTE_DELETAR = "ms-gerente.gerente.deletar.queue";
  public static final String FILA_SAGA_GERENTE_ADICIONAR = "ms-saga.gerente.adicionar.queue";
  public static final String FILA_SAGA_CONTAS_TRANSFERIDAS = "ms-saga.contas.transferidas.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERENTE_DELETADA = "ms-saga.credencial.gerente.deletada.queue";
  public static final String FILA_CONTAS_TRANSFERIR = "ms-saga.contas.transferir.queue";
  public static final String FILA_SAGA_CONTA_ATRIBUIR_FALHA = "ms-saga.conta.atribuir.falha.queue";
  public static final String FILA_CONTA_ATRIBUIR = "ms-saga.conta.atribuir.queue";
  public static final String FILA_SAGA_GERENTE_CREDENCIAL_GERADA = "ms-saga.gerente.credencial.gerada.queue";
  public static final String FILA_SAGA_GERENTE_CREDENCIAL_FALHA = "ms-saga.gerente.credencial.gerada.falha.queue";
  public static final String FILA_CREDENCIAL_GERENTE_GERAR = "ms-saga.credencial.gerente.gerar.queue";
  public static final String FILA_CREDENCIAL_GERENTE_REMOVER = "ms-saga.credencial.gerente.remover.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERENTE_REMOVIDA = "ms-saga.credencial.gerente.removida.queue";

  // FILAS MS-AUTH
  public static final String FILA_SAGA_CREDENCIAL_CLIENTE_GERADA_FALHA = "ms-saga.credencial.cliente.falha.queue";
  public static final String FILA_CREDENCIAL_CLIENTE_GERAR = "ms-saga.credencial.cliente.gerar.queue";


  // FILAS MS-EMAIL
  public static final String FILA_EMAIL_ENVIAR = "ms-email.email.enviar.queue";

  // FILAS DO MS-CONTA (O Trabalhador)
  // O MS-Conta escuta COMANDOS (Ordens)
  public static final String FILA_CONTA_CRIAR = "ms-conta.conta.criar.queue";
  public static final String FILA_CONTA_REVERTER = "ms-conta.conta.status.reverter.queue";

  // FILAS DO MS-AUTH (O Trabalhador)

  // FILAS DO MS-CLIENTE (O Trabalhador)
  public static final String FILA_CLIENTE_DELETAR = "ms-cliente.cliente.deletar.queue";
  // FALLBACK QUEUES


  private RabbitMQConstants() {} // Evita que a classe seja instanciada
}
