package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroSagaConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CLIENTE_CRIADO),
      key = RabbitMQConstants.RK_CLIENTE_CRIADO_SUCESSO
    )
  )
  public void ReceiveCreateClienteProfileEvent(String message) throws JsonProcessingException {

      System.out.println("Evento de criação de cliente. Enviando comando para Ms-Conta!");

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CONTA_CRIAR_COMANDO,
        message
      );

      System.out.println("Evento enviado com sucesso!");
  }

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_SAGA_CONTA_CRIADA_FALHA),
      key = RabbitMQConstants.RK_CONTA_CRIADA_FALHA
    )
  )
  public void HandleCreateContaErrorEvent(String message){
    System.out.println("Evento de falha na criação de conta recebido. Enviando evento de deleção de conta do cliente");

    rabbitTemplate.convertAndSend(
      RabbitMQConstants.BANTADS_EXCHANGE,
      RabbitMQConstants.RK_CLIENTE_DELETAR_COMANDO,
      message
    );

    System.out.println("Evento enviado com sucesso!");
  }

}
