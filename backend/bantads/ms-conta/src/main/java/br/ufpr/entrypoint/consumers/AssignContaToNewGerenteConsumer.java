package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsConta;
import br.ufpr.core.domain.AssignGerenteToContaInputData;
import br.ufpr.core.ports.input.AssignNewGerenteToContaInputPort;
import br.ufpr.model.message.AssignGerenteToContaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignContaToNewGerenteConsumer {

  private final ObjectMapper objectMapper;
  private final AssignNewGerenteToContaInputPort assignNewGerenteToContaInputPort;

  @RabbitListener(queues = RabbitMQConfigMsConta.ASSIGN_ACCOUNT_TO_NEW_MANAGER_QUEUE)
  public void receiveEvent(String message) throws JsonProcessingException{

    try{

      AssignGerenteToContaMessage payload = objectMapper.readValue(message, AssignGerenteToContaMessage.class);

      System.out.println("[MS-CONTA-ASSIGN-CONTA] Dados do gerente recebidos. Id: " + payload.getGerenteId());

      AssignGerenteToContaInputData inputData = new AssignGerenteToContaInputData();

      inputData.setGerenteId(payload.getGerenteId());

      assignNewGerenteToContaInputPort.execute(inputData);

    }catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
