package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSagaConsumer {

  @Autowired
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_EMAIL_NOTIFICACAO_PRONTA),
      key = RabbitMQConstants.RK_NOTIFICACAO_PRONTA_EVENTO
    )
  )
  public void updateCredential(String message){

    System.out.println("Evento 'Notificacao Pronta' recebido. Mandando mensagem para ms-email");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_EMAIL_ENVIAR_COMANDO,
      message
    );
  }

}
