package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreatePendingContaInputPort;
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
public class CreateAccountConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final CreatePendingContaInputPort createPendingContaInputPort;

    @RabbitListener(bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CONTA_CRIAR),
      key = RabbitMQConstants.RK_CONTA_CRIAR_COMANDO
    ))
    public void receivePedido(String message) throws JsonProcessingException {

      try {
        TransferClienteDataSagaMessage payload = objectMapper.readValue(message, TransferClienteDataSagaMessage.class);
        TransferClienteDataInputData inputData = new TransferClienteDataInputData();

        inputData.setClienteId(payload.getClienteId());
        inputData.setSalario(payload.getSalario());

        createPendingContaInputPort.execute(inputData);

      } catch (Exception e) {

        rabbitTemplate.convertAndSend(
          RabbitMQConstants.BANTADS_EXCHANGE,
          RabbitMQConstants.RK_CONTA_CRIADA_FALHA,
          message
        );

        throw new AmqpRejectAndDontRequeueException(e);
      }

    }
}
