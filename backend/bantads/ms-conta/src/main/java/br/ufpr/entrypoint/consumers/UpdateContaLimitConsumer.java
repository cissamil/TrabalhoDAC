package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsConta;
import br.ufpr.core.domain.UpdateContaLimitInputData;
import br.ufpr.core.ports.input.UpdateContaLimitInputPort;
import br.ufpr.model.message.UpdateContaLimitMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateContaLimitConsumer {

  private final ObjectMapper objectMapper;
  private final UpdateContaLimitInputPort updateContaLimitInputPort;

  @RabbitListener(queues = RabbitMQConfigMsConta.UPDATE_ACCOUNT_LIMIT_QUEUE)
  public void receiveEvent(String message) throws JsonProcessingException{
    try{

      UpdateContaLimitMessage payload = objectMapper.readValue(message, UpdateContaLimitMessage.class);


      UpdateContaLimitInputData inputData = new UpdateContaLimitInputData();

      inputData.setClienteId(payload.getClienteId());
      inputData.setClienteSalary(payload.getClienteSalary());

      updateContaLimitInputPort.execute(inputData);
    }catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
