package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.NotifyClienteInputData;
import br.ufpr.core.ports.input.PrepareClienteNotificationInputPort;
import br.ufpr.model.message.NotifyClienteMessage;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyClienteConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final PrepareClienteNotificationInputPort prepareClienteNotificationInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CLIENTE_PREPARAR_NOTIFICACAO),
      key = RabbitMQConstants.RK_CLIENTE_PREPARAR_NOTIFICACAO_COMANDO
    )
  )
  public void notifyClienteConsumer(String message) throws JsonProcessingException {

      try {

        NotifyClienteMessage payload = objectMapper.readValue(message, NotifyClienteMessage.class);

        NotifyClienteInputData inputData = new NotifyClienteInputData();

        inputData.setClienteId(payload.getClienteId());
        inputData.setMessage(payload.getMessage());

        prepareClienteNotificationInputPort.execute(inputData);



      } catch (Exception e) {

        NotifyClienteMessage payload = objectMapper.readValue(message, NotifyClienteMessage.class);

        TransferClienteIdSagaMessage transferClienteIdSagaMessage = new TransferClienteIdSagaMessage(payload.getClienteId());

        String rollbackMessage = objectMapper.writeValueAsString(transferClienteIdSagaMessage);

        rabbitTemplate.convertAndSend(
          RabbitMQConstants.BANTADS_EXCHANGE,
          RabbitMQConstants.RK_CLIENTE_NOTIFICACAO_FALHA_EVENTO,
          rollbackMessage
        );

        throw new AmqpRejectAndDontRequeueException(e);
      }
    }
  }


