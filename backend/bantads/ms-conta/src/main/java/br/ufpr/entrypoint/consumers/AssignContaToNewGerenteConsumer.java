package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.AssignGerenteToContaInputData;
import br.ufpr.core.ports.input.AssignNewGerenteToContaInputPort;
import br.ufpr.model.message.AssignGerenteToContaMessage;
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
public class AssignContaToNewGerenteConsumer {

  private final ObjectMapper objectMapper;
  private final RabbitTemplate rabbitTemplate;
  private final AssignNewGerenteToContaInputPort assignNewGerenteToContaInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CONTA_ATRIBUIR),
      key = RabbitMQConstants.RK_CONTA_ATRIBUIR_COMANDO
    )
  )
  public void receiveEvent(String message) throws JsonProcessingException{

    try{

      AssignGerenteToContaMessage payload = objectMapper.readValue(message, AssignGerenteToContaMessage.class);

      System.out.println("[MS-CONTA-ASSIGN-CONTA] Dados do gerente recebidos. Id: " + payload.getGerenteId());

      AssignGerenteToContaInputData inputData = new AssignGerenteToContaInputData();

      inputData.setGerenteId(payload.getGerenteId());

      assignNewGerenteToContaInputPort.execute(inputData);

    }catch (Exception e){

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CONTA_ATRIBUIR_FALHA_EVENTO,
        message
      );

      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
