package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsAuth;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.ports.input.PrepareGerenteCredentialInputPort;
import br.ufpr.model.message.GenerateGerenteCredentialEventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGerenteCredentialConsumer {


  private final ObjectMapper objectMapper;
  private final PrepareGerenteCredentialInputPort prepareGerenteCredentialInputPort;

  @RabbitListener(queues = RabbitMQConfigMsAuth.GENERATE_MANAGER_CREDENTIAL_QUEUE)
  public void criarCredencial(String message) throws JsonProcessingException {

    try{

      GenerateGerenteCredentialEventMessage payload = objectMapper.readValue(message, GenerateGerenteCredentialEventMessage.class);

      GerenteInputData inputData = new GerenteInputData();

      inputData.setEmail(payload.getEmail());
      inputData.setGerenteId(payload.getGerenteId());
      inputData.setSenha(payload.getSenha());

      System.out.println("Dados do gerente recebidos: " + inputData.getGerenteId());

      prepareGerenteCredentialInputPort.execute(inputData);

    } catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
