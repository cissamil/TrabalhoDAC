package br.ufpr.consumers;

import br.ufpr.config.RabbitMQConfigMsAuth;
import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.ports.input.PrepareClienteCredentialInputPort;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateClienteCredentialConsumer {

  private final ObjectMapper objectMapper;
  private final PrepareClienteCredentialInputPort prepareClienteCredentialInputPort;

  @RabbitListener(queues = RabbitMQConfigMsAuth.APPROVED_ACCOUNT_QUEUE)
  public void criarCredencial(String message) throws JsonProcessingException {

    try{

      TransferClienteIdSagaMessage payload = objectMapper.readValue(message, TransferClienteIdSagaMessage.class);

      TransferClienteIdInputData inputData = new TransferClienteIdInputData();
      inputData.setClienteId(payload.getClienteId());

      System.out.println("Id de cliente recebido. Gerando conta. Id: " + inputData.getClienteId());

      prepareClienteCredentialInputPort.execute(inputData);

    } catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
