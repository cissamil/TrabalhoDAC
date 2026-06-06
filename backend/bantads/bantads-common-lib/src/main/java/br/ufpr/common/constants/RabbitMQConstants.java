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
  public static final String RK_CONTA_CRIADA_SUCESSO = "conta.criada.sucesso.evento";
  public static final String RK_CONTA_CRIADA_FALHA = "conta.criada.falha.evento";
  public static final String RK_CONTA_APROVADA_SUCESSO = "conta.aprovada.sucesso";
  public static final String RK_CONTA_REVERTER_APROVACAO_COMANDO = "conta.reverter.aprovacao.comando";


  public static final String RK_CONTA_ATRIBUIR_GERENTE_COMANDO = "conta.atribuir.gerente.comando";
  public static final String RK_CONTA_STATUS_MUDAR_COMANDO = "conta.status.mudar.comando";

  // AUTH
  public static final String RK_CLIENTE_CREDENCIAL_CRIAR_COMANDO = "cliente.credencial.criar.comando";
  public static final String RK_CLIENTE_CREDENCIAL_GERADA_FALHA = "cliente.credencial.falha.evento";



  public static final String RK_CREDENCIAL_GERAR_COMANDO = "credencial.gerar.comando";
  public static final String RK_CREDENCIAL_GERADA_SUCESSO = "credencial.gerada.sucesso.evento";
  public static final String RK_CREDENCIAL_GERADA_FALHA = "credencial.gerada.falha.evento";
  public static final String RK_CREDENCIAL_DELETAR_COMANDO = "credencial.deletar.comando";

  public static final String RK_GERENTE_REMOVER_COMANDO = "gerente.remover.comando";
  public static final String RK_GERENTE_REMOVIDO_SUCESSO = "gerente.removido.sucesso.evento";


  // QUEUES
  public static final String FILA_SAGA_CLIENTE_CRIADO = "ms-saga.cliente.criado.queue";
  public static final String FILA_SAGA_CONTA_CRIADA_FALHA = "ms-saga.conta.criada.falha.queue";
  public static final String FILA_CONTA_APROVADA = "ms-saga.conta.aprovada.queue";
  public static final String FILA_SAGA_CONTA_CRIADA_SUCESSO = "ms-saga.conta.criada.sucesso.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERADA_SUCESSO = "ms-saga.credencial.gerada.sucesso.queue";
  public static final String FILA_SAGA_CREDENCIAL_GERADA_FALHA = "ms-saga.credencial.gerada.falha.queue";

  // FILAS MS-AUTH
  public static final String FILA_CREDENCIAL_CLIENTE_GERADA_FALHA = "ms-saga.credencial.cliente.falha.queue";
  public static final String FILA_CREDENCIAL_CLIENTE_GERAR = "ms-saga.credencial.cliente.gerar.queue";


  // FILAS MS-EMAIL
  public static final String FILA_EMAIL_ENVIAR = "ms-email.email.enviar.queue";

  // FILAS DO MS-CONTA (O Trabalhador)
  // O MS-Conta escuta COMANDOS (Ordens)
  public static final String FILA_CONTA_CRIAR = "ms-conta.conta.criar.queue";
  public static final String FILA_CONTA_ATRIBUIR_GERENTE = "ms-conta.conta.atribuir.gerente.queue";
  public static final String FILA_CONTA_REVERTER = "ms-conta.conta.status.reverter.queue";

  // FILAS DO MS-AUTH (O Trabalhador)
  public static final String FILA_AUTH_CREDENCIAL_GERAR = "ms-auth.credencial.gerar.queue";
  public static final String FILA_AUTH_CREDENCIAL_DELETAR = "ms-auth.credencial.deletar.queue";

  // FILAS DO MS-CLIENTE (O Trabalhador)
  public static final String FILA_CLIENTE_DELETAR = "ms-cliente.cliente.deletar.queue";
  // FALLBACK QUEUES


  private RabbitMQConstants() {} // Evita que a classe seja instanciada
}
