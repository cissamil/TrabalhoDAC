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
public class RefuseContaSagaConsumer {

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CONTA_REJEITADA),
      key = RabbitMQConstants.RK_CONTA_REJEITADA_EVENTO
    )
  )
  public void createCredential(String message){

    System.out.println("Evento 'Conta Rejeitada' recebido. Enviando comando para ms-cliente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CLIENTE_PREPARAR_NOTIFICACAO_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CLIENTE_NOTIFICACAO_PRONTA),
      key = RabbitMQConstants.RK_CLIENTE_NOTIFICACAO_PRONTA_EVENTO
    )
  )
  public void sendEmail(String message){

    System.out.println("Evento 'Notificação Pronta' recebido. Enviando comando para ms-email");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_EMAIL_ENVIAR_MAS_NOTICIAS_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_ENVIAR_MAS_NOTICIAS_FALHA),
      key = RabbitMQConstants.RK_EMAIL_MAS_NOTICIAS_FALHA_EVENTO
    )
  )
  public void fallbackSendEmail(String message){

    System.out.println("Evento 'Falha ao Enviar Email' recebido. Enviando comando para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTA_REVERTER_STATUS_COMANDO,
      message
    );
  }


  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CLIENTE_PREPARAR_NOTIFICACAO_FALHA),
      key = RabbitMQConstants.RK_CLIENTE_NOTIFICACAO_FALHA_EVENTO
    )
  )
  public void fallbackPrepareNotification(String message){

    System.out.println("Evento 'Falha ao preparar notificação' recebido. Enviando comando para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTA_REVERTER_STATUS_COMANDO,
      message
    );
  }


}
