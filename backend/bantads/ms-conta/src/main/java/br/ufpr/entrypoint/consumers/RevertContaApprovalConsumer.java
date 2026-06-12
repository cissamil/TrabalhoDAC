package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.input.RevertContaApprovalInputPort;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
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
public class RevertContaApprovalConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final RevertContaApprovalInputPort revertContaApprovalInputPort;

  @RabbitListener(bindings = @QueueBinding(
    exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
    value = @Queue(RabbitMQConstants.FILA_CONTA_REVERTER),
    key = RabbitMQConstants.RK_CONTA_REVERTER_STATUS_COMANDO
  ))
  public void receiveCommand(String message) throws JsonProcessingException {
    try {
      TransferClienteIdSagaMessage payload = objectMapper.readValue(message, TransferClienteIdSagaMessage.class);

      String clienteId = payload.getClienteId();

      System.out.println("Comando de reversão recebido. Revertendo conta do cliente " + clienteId);

      revertContaApprovalInputPort.execute(clienteId);

    } catch (Exception e) {

      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
