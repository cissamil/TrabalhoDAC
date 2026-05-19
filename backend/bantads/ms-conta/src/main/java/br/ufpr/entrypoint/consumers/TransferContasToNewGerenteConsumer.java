package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsConta;
import br.ufpr.core.domain.TransferContasToGerenteInputData;
import br.ufpr.core.ports.output.TransferContasToNewGerenteInputPort;
import br.ufpr.model.message.TransferContasToGerenteMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferContasToNewGerenteConsumer {

  private final ObjectMapper objectMapper;
  private final TransferContasToNewGerenteInputPort transferContasToNewGerenteInputPort;

  @RabbitListener(queues = RabbitMQConfigMsConta.TRANSFER_ACCOUNTS_TO_MANAGER_EXCHANGE)
  public void receiveEvent(String message) throws JsonProcessingException{

    try{

      TransferContasToGerenteMessage payload = objectMapper.readValue(message, TransferContasToGerenteMessage.class);

      System.out.println("[MS-CONTA] Dados do gerente recebidos. Id: " + payload.getGerenteId());

      TransferContasToGerenteInputData inputData = new TransferContasToGerenteInputData();

      inputData.setGerenteId(payload.getGerenteId());

      transferContasToNewGerenteInputPort.execute(inputData);

    }catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
