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

  // @TODO TERMINAR ESSE CONSUMER QUANDO ARRUMAR A ROTA DE RECUSAR CONTA

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CONTA_REPROVADA),
      key = RabbitMQConstants.RK_CONTA_APROVADA_SUCESSO
    )
  )
  public void createCredential(String message){

    System.out.println("Evento de conta criada recebido. Enviando comando para ms-auth");


    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CLIENTE_CREDENCIAL_CRIAR_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CREDENCIAL_CLIENTE_GERADA_FALHA),
      key = RabbitMQConstants.RK_CLIENTE_CREDENCIAL_GERADA_FALHA
    )
  )
  public void desaproveConta(String message){

    System.out.println("Evento de fallback na criação da credencial recebido. Enviando comando para ms-conta");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTA_REVERTER_STATUS_COMANDO,
      message
    );
  }


}
