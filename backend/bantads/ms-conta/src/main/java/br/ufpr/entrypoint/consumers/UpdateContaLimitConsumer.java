package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.UpdateContaLimitInputData;
import br.ufpr.core.ports.input.UpdateContaLimitInputPort;
import br.ufpr.model.message.UpdateContaLimitMessage;
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
public class UpdateContaLimitConsumer {

  private final ObjectMapper objectMapper;
  private final UpdateContaLimitInputPort updateContaLimitInputPort;

  @RabbitListener(bindings = @QueueBinding(
    exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
    value = @Queue(RabbitMQConstants.FILA_CONTA_LIMITE_ATUALIZAR),
    key = RabbitMQConstants.RK_CONTA_LIMITE_ATUALIZAR_COMANDO
  ))
  public void receiveEvent(String message) throws JsonProcessingException{
    try{

      System.out.println("Comando 'atualizar limite da conta' recebido. Executando tarefa...");

      UpdateContaLimitMessage payload = objectMapper.readValue(message, UpdateContaLimitMessage.class);


      UpdateContaLimitInputData inputData = new UpdateContaLimitInputData();

      inputData.setClienteId(payload.getClienteId());
      inputData.setClienteSalary(payload.getClienteSalary());

      updateContaLimitInputPort.execute(inputData);

      System.out.println("Novo limite da conta atualizado com sucesso!");

    }catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
