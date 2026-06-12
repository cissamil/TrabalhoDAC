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
public class RemoveGerenteSagaConsumer {

  // @TODO CRIAR COMANDO PARA DELETAR CREDENCIAL DO GERENTE

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_GERENTE_REMOVER),
      key = RabbitMQConstants.RK_GERENTE_REMOVER_EVENTO
    )
  )
  public void transferContas(String message){

    System.out.println("Evento de remoção de gerente recebido. Enviando mensagem para ms-conta");


    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CONTAS_TRANSFERIR_COMANDO,
      message
    );
  }


  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CONTAS_TRANSFERIDAS),
      key = RabbitMQConstants.RK_CONTAS_TRANSFERIDAS_SUCESSO
    )
  )
  public void deleteCredential(String message){

    System.out.println("Evento 'Contas Transferidas' recebido. Enviando mensagem para ms-auth");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_CREDENCIAL_REMOVER_COMANDO,
      message
    );
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CREDENCIAL_GERENTE_DELETADA),
      key = RabbitMQConstants.RK_CREDENCIAL_GERENTE_DELETADA_EVENTO
    )
  )
  public void removeGerente(String message){

    System.out.println("Evento 'Credencial Deletada' recebido. Enviando mensagem para ms-gerente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_GERENTE_REMOVER_COMANDO,
      message
    );
  }


}
