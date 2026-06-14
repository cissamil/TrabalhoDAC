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
public class EditClienteInfoSagaConsumer {

  @Autowired
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CLIENTE_EMAIL_ATUALIZADO),
      key = RabbitMQConstants.RK_CLIENTE_EMAIL_ATUALIZADO_EVENTO
    )
  )
  public void updateCredential(String message){

    System.out.println("Evento 'email do cliente atualizado' recebido. Mandando mensagem para ms-auth");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CLIENTE_CREDENCIAL_ATUALIZAR_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CLIENTE_SALARIO_ATUALIZADO),
      key = RabbitMQConstants.RK_CLIENTE_SALARIO_ATUALIZADO_EVENTO
    )
  )
  public void assignConta(String message){

    System.out.println("Evento 'salario cliente atualizado' recebido. Enviando mensagem para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTA_LIMITE_ATUALIZAR_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CREDENCIAL_CLIENTE_ATUALIZADA_FALHA),
      key = RabbitMQConstants.RK_CLIENTE_CREDENCIAL_ATUALIZADA_FALHA
    )
  )
  public void revertCredential(String message){

    System.out.println("Evento 'falha ao atualizar credencial' recebido. Enviando mensagem para ms-cliente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CLIENTE_EMAIL_REVERTER_COMANDO,
      message
    );
  }
}
