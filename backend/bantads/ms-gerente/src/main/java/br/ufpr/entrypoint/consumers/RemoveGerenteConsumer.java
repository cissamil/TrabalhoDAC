package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.input.RemoveGerenteInputPort;
import br.ufpr.model.message.TransferGerenteIdMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveGerenteConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final RemoveGerenteInputPort removeGerenteInputPort;

  @RabbitListener(bindings = @QueueBinding(
    exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
    value = @Queue(RabbitMQConstants.FILA_GERENTE_DELETAR),
    key = RabbitMQConstants.RK_GERENTE_REMOVER_COMANDO
  ))
  public void receiveCommand(String message) throws JsonProcessingException {

    System.out.println("Comando para deletar gerente recebido.");
    System.out.println("Mensagem: " + message);
    try {
      TransferGerenteIdMessage payload = objectMapper.readValue(message, TransferGerenteIdMessage.class);

      String gerenteId = payload.getGerenteId();

      System.out.println("Comando de remoção de gerente recebido. Removendo gerente: " + gerenteId);

      removeGerenteInputPort.execute(gerenteId);

    } catch (Exception e) {

      throw new AmqpRejectAndDontRequeueException(e);
    }

  }


}
