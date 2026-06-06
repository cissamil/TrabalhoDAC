package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsAuth;
import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.domain.UpdateUserEmailInputData;
import br.ufpr.core.ports.input.PrepareClienteCredentialInputPort;
import br.ufpr.core.ports.input.UpdateUserEmailInputPort;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import br.ufpr.model.message.UpdateUserEmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailConsumer {

  private final ObjectMapper objectMapper;
  private final UpdateUserEmailInputPort updateUserEmailInputPort;

  @RabbitListener(queues = RabbitMQConfigMsAuth.UPDATE_USER_EMAIL_QUEUE)
  public void updateUserEmail(String message) throws JsonProcessingException {
    try{

      UpdateUserEmailMessage payload = objectMapper.readValue(message, UpdateUserEmailMessage.class);

      UpdateUserEmailInputData inputData = new UpdateUserEmailInputData();

      System.out.println("Atualizando email do usuário");

      inputData.setUserId(payload.getUserId());
      inputData.setUserEmail(payload.getUserEmail());

      updateUserEmailInputPort.execute(inputData);

    } catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }
  }
}
