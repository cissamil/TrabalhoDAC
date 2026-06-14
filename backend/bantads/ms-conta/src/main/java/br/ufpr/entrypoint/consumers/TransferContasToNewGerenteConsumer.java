package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.TransferContasToGerenteInputData;
import br.ufpr.core.ports.output.TransferContasToNewGerenteInputPort;
import br.ufpr.model.message.TransferContasToGerenteMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferContasToNewGerenteConsumer {

  private final ObjectMapper objectMapper;
  private final TransferContasToNewGerenteInputPort transferContasToNewGerenteInputPort;

  @RabbitListener(bindings = @QueueBinding(
    exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
    value = @Queue(RabbitMQConstants.FILA_CONTAS_TRANSFERIR),
    key = RabbitMQConstants.RK_CONTAS_TRANSFERIR_COMANDO
  ))
  public void receiveEvent(String message) throws JsonProcessingException{

    System.out.println("Evento de transferência de contas recebido com sucesso");

    try{

      TransferContasToGerenteMessage payload = objectMapper.readValue(message, TransferContasToGerenteMessage.class);

      System.out.println("[MS-CONTA-TRANSFER-CONTAS] Dados do gerente recebidos. Id: " + payload.getGerenteId());

      TransferContasToGerenteInputData inputData = new TransferContasToGerenteInputData();

      inputData.setGerenteId(payload.getGerenteId());

      transferContasToNewGerenteInputPort.execute(inputData);

    }catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
