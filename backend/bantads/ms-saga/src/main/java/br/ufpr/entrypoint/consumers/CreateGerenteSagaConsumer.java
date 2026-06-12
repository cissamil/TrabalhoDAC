package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGerenteSagaConsumer {

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_GERENTE_ADICIONAR),
      key = RabbitMQConstants.RK_GERENTE_CRIADO_EVENTO
    )
  )
  public void generateCredential(String message){

    System.out.println("Evento 'gerente criado' recebido. Mandando mensagem para ms-auth");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_CREDENCIAL_CRIAR_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_GERENTE_CREDENCIAL_GERADA),
      key = RabbitMQConstants.RK_GERENTE_CREDENCIAL_GERADA_EVENTO
    )
  )
  public void assignConta(String message){

    System.out.println("Evento 'credencial gerada' recebido. Enviando mensagem para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTA_ATRIBUIR_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_GERENTE_CREDENCIAL_FALHA),
      key = RabbitMQConstants.RK_GERENTE_CREDENCIAL_GERADA_FALHA
    )
  )
  public void rollbackCredencial(String message){

    System.out.println("Evento 'falha na credencial' recebido. Enviando mensagem para ms-gerente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_REMOVER_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CREDENCIAL_GERENTE_REMOVIDA),
      key = RabbitMQConstants.RK_GERENTE_CREDENCIAL_DELETADA_EVENTO
    )
  )
  public void deleteGerente(String message){

    System.out.println("Evento 'credencial deletada' recebido. Enviando mensagem para ms-gerente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_REMOVER_COMANDO,
      message
    );
  }



  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CONTA_ATRIBUIR_FALHA),
      key = RabbitMQConstants.RK_CONTA_ATRIBUIR_FALHA_EVENTO
    )
  )
  public void rollbackAssignConta(String message){

    System.out.println("Evento 'falha na atribuição de contas' recebido. Enviando mensagem para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_CREDENCIAL_REMOVER_COMANDO,
      message
    );
  }
}
