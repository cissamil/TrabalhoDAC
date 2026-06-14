package br.ufpr.common.constants;

public final class RabbitMQConstants {

  // Exchanges
  public static final String BANTADS_EXCHANGE = "bantads.exchange";

  // ROUTING KEYS

  // CLIENTE
  public static final String RK_CLIENTE_CRIADO_SUCESSO = "cliente.criado.sucesso.evento";
  public static final String RK_CLIENTE_DELETAR_COMANDO = "cliente.deletar.comando";
  public static final String RK_CLIENTE_PREPARAR_NOTIFICACAO_COMANDO = "cliente.preparar.notificacao.comando";
  public static final String RK_CLIENTE_NOTIFICACAO_PRONTA_EVENTO = "cliente.notificacao.pronta.evento";
  public static final String RK_CLIENTE_NOTIFICACAO_FALHA_EVENTO = "cliente.notificacao.falha.evento";



  //EMAIL
  public static final String RK_EMAIL_ENVIAR_MAS_NOTICIAS_COMANDO = "email.enviar.mas.noticias.comando";
  public static final String RK_NOTIFICACAO_PRONTA_EVENTO = "notificacao.pronta.evento";
  public static final String RK_EMAIL_ENVIAR_COMANDO = "email.enviar.comando";
  public static final String RK_EMAIL_MAS_NOTICIAS_FALHA_EVENTO = "email.mas.noticias.falha.evento";

  // CONTA
  public static final String RK_CONTA_CRIAR_COMANDO = "conta.criar.comando";
  public static final String RK_CONTA_CRIADA_FALHA = "conta.criada.falha.evento";
  public static final String RK_CONTA_APROVADA_SUCESSO = "conta.aprovada.sucesso";
  public static final String RK_CONTA_REVERTER_STATUS_COMANDO = "conta.reverter.status.comando";
  public static final String RK_CONTA_SYNC_EVENTO = "conta.sync.evento";
  public static final String RK_BATCH_CONTA_SYNC_EVENTO = "batch.conta.sync.evento";
  public static final String RK_CONTA_LIMITE_ATUALIZAR_COMANDO = "conta.limite.atualizar.comando";

  public static final String RK_CONTA_REJEITADA_EVENTO = "conta.rejeitada.evento";

  public static final String RK_CONTAS_TRANSFERIR_COMANDO = "contas.transferir.comando";
  public static final String RK_CONTAS_TRANSFERIDAS_SUCESSO = "contas.transferidas.sucesso";
  public static final String RK_CREDENCIAL_GERENTE_DELETADA_EVENTO = "credencial.gerente.deletada.evento";
  public static final String RK_GERENTE_REMOVER_EVENTO = "gerente.remover.evento";
  public static final String RK_GERENTE_REMOVER_COMANDO = "gerente.remover.comando";
  public static final String RK_GERENTE_CREDENCIAL_REMOVER_COMANDO = "gerente.credencial.remover.comando";

  public static final String RK_GERENTE_CRIADO_EVENTO = "gerente.criado.evento";
  public static final String RK_CONTA_ATRIBUIR_COMANDO = "conta.atribuir.comando";
  public static final String RK_CONTA_ATRIBUIR_FALHA_EVENTO = "conta.atribuir.falha.evento";
  public static final String RK_CLIENTE_EMAIL_ATUALIZADO_EVENTO = "cliente.email.atualizado.evento";
  public static final String RK_CLIENTE_SALARIO_ATUALIZADO_EVENTO = "cliente.salario.atualizado.evento";



  // AUTH
  public static final String RK_CLIENTE_CREDENCIAL_CRIAR_COMANDO = "cliente.credencial.criar.comando";
  public static final String RK_CLIENTE_CREDENCIAL_ATUALIZAR_COMANDO = "cliente.credencial.atualizar.comando";
  public static final String RK_CLIENTE_CREDENCIAL_GERADA_FALHA = "cliente.credencial.falha.evento";
  public static final String RK_CLIENTE_CREDENCIAL_ATUALIZADA_FALHA = "cliente.credencial.atualizada.falha.evento";
  public static final String RK_CLIENTE_EMAIL_REVERTER_COMANDO = "cliente.email.reverter.comando";

  public static final String RK_GERENTE_CREDENCIAL_CRIAR_COMANDO = "gerente.credencial.criar.comando";
  public static final String RK_GERENTE_CREDENCIAL_GERADA_EVENTO = "gerente.credencial.gerada.evento";
  public static final String RK_GERENTE_CREDENCIAL_DELETADA_EVENTO = "gerente.credencial.evento.evento";
  public static final String RK_GERENTE_CREDENCIAL_GERADA_FALHA = "gerente.credencial.falha.evento";

  // QUEUES MS-SAGA
  public static final String FILA_SAGA_CLIENTE_CRIADO = "ms-saga.cliente.criado.queue";
  public static final String FILA_SAGA_CLIENTE_EMAIL_ATUALIZADO = "ms-saga.cliente.email.atualizado.queue";
  public static final String FILA_SAGA_CLIENTE_SALARIO_ATUALIZADO = "ms-saga.cliente.salario.atualizado.queue";
  public static final String FILA_SAGA_CONTA_CRIADA_FALHA = "ms-saga.conta.criada.falha.queue";
  public static final String FILA_SAGA_CONTA_APROVADA = "ms-saga.conta.aprovada.queue";
  public static final String FILA_SAGA_CONTA_REJEITADA = "ms-saga.conta.rejeitada.queue";
  public static final String FILA_SAGA_GERENTE_REMOVER = "ms-saga.gerente.remover.queue";
  public static final String FILA_SAGA_CONTAS_TRANSFERIDAS = "ms-saga.contas.transferidas.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERENTE_DELETADA = "ms-saga.credencial.gerente.deletada.queue";
  public static final String FILA_SAGA_GERENTE_ADICIONAR = "ms-saga.gerente.adicionar.queue";
  public static final String FILA_SAGA_GERENTE_CREDENCIAL_GERADA = "ms-saga.gerente.credencial.gerada.queue";
  public static final String FILA_SAGA_GERENTE_CREDENCIAL_FALHA = "ms-saga.gerente.credencial.gerada.falha.queue";
  public static final String FILA_CREDENCIAL_GERENTE_GERAR = "ms-saga.credencial.gerente.gerar.queue";
  public static final String FILA_CONTA_ATRIBUIR = "ms-saga.conta.atribuir.queue";
  public static final String FILA_CONTAS_TRANSFERIR = "ms-saga.contas.transferir.queue";
  public static final String FILA_SAGA_CONTA_ATRIBUIR_FALHA = "ms-saga.conta.atribuir.falha.queue";
  public static final String FILA_CREDENCIAL_GERENTE_REMOVER = "ms-saga.credencial.gerente.remover.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERENTE_REMOVIDA = "ms-saga.credencial.gerente.removida.queue";
  public static final String FILA_SAGA_CLIENTE_NOTIFICACAO_PRONTA = "ms-saga.cliente.notificacao.pronta.queue";
  public static final String FILA_SAGA_ENVIAR_MAS_NOTICIAS_FALHA = "ms-saga.email.enviar.mas.noticias.falha.queue";
  public static final String FILA_SAGA_CLIENTE_PREPARAR_NOTIFICACAO_FALHA = "ms-saga.cliente.preparar.notificacao.falha.queue";
  public static final String FILA_SAGA_CREDENCIAL_CLIENTE_GERADA_FALHA = "ms-saga.credencial.cliente.falha.queue";
  public static final String FILA_SAGA_CREDENCIAL_CLIENTE_ATUALIZADA_FALHA = "ms-saga.credencial.cliente.atualizada.falha.queue";
  public static final String FILA_SAGA_EMAIL_NOTIFICACAO_PRONTA = "ms-saga.notificacao.pronta.queue";


  public static final String FILA_GERENTE_DELETAR = "ms-gerente.gerente.deletar.queue";

  // FILAS MS-AUTH
  public static final String FILA_CREDENCIAL_CLIENTE_GERAR = "ms-auth.credencial.cliente.gerar.queue";
  public static final String FILA_CREDENCIAL_CLIENTE_ATUALIZAR = "ms-auth.credencial.cliente.atualizar.queue";


  // FILAS MS-EMAIL
  public static final String FILA_EMAIL_ENVIAR_MAS_NOTICIAS = "ms-email.email.enviar.mas.noticias.queue";
  public static final String FILA_EMAIL_ENVIAR_NOTIFICACAO = "ms-email.email.enviar.notificacao.queue";

  // FILAS MS-CONTA
  public static final String FILA_CONTA_CRIAR = "ms-conta.conta.criar.queue";
  public static final String FILA_CONTA_REVERTER = "ms-conta.conta.status.reverter.queue";
  public static final String FILA_BATCH_CONTA_SYNC = "ms-conta.batch.conta.sync.queue";
  public static final String FILA_CONTA_SYNC = "ms-conta.conta.sync.queue";
  public static final String FILA_CONTA_LIMITE_ATUALIZAR = "ms-conta.conta.limite.atualizar.queue";


  // FILAS DO MS-CLIENTE (O Trabalhador)
  public static final String FILA_CLIENTE_DELETAR = "ms-cliente.cliente.deletar.queue";
  public static final String FILA_CLIENTE_EMAIL_REVERTER = "ms-cliente.cliente.email.reverter";
  public static final String FILA_CLIENTE_PREPARAR_NOTIFICACAO = "ms-cliente.cliente.preparar.notificacao.queue";


  private RabbitMQConstants() {}
}
