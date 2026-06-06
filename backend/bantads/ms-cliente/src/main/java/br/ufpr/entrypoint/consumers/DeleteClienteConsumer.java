package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.RemoveClienteInputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteClienteConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final RemoveClienteInputPort removeClienteInputport;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CLIENTE_DELETAR),
      key = RabbitMQConstants.RK_CLIENTE_DELETAR_COMANDO
    )
  )
  public void deleteClienteConsumer(String message) throws JsonProcessingException {

      try {

        TransferClienteDataSagaMessage payload = objectMapper.readValue(message, TransferClienteDataSagaMessage.class);

        System.out.println("Fallback de cliente acionado. Removendo cliente recém-criado");

        TransferClienteDataInputData inputData = new TransferClienteDataInputData();

        inputData.setClienteId(payload.getClienteId());
        inputData.setSalario(payload.getSalario());

        removeClienteInputport.execute(inputData);

      } catch (Exception e) {
        throw new AmqpRejectAndDontRequeueException(e);
      }
    }
  }


