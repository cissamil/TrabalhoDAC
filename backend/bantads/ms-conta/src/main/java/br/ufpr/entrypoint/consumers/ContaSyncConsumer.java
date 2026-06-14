package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.SyncContaInputData;
import br.ufpr.core.ports.input.SyncContaInputPort;
import br.ufpr.entrypoint.request.SyncContaMessage;
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
public class ContaSyncConsumer {

  private final ObjectMapper objectMapper;
  private final RabbitTemplate rabbitTemplate;
  private final SyncContaInputPort syncContaInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CONTA_SYNC),
      key = RabbitMQConstants.RK_CONTA_SYNC_EVENTO
    )
  )
  public void receiveEvent(String message) throws JsonProcessingException{

    try{

      System.out.println("\n\n\n|-------------------------------------------------------------|");
      System.out.println("Evento de sincronia recebido. Sincronizando bancos de dados");
      System.out.println("|-------------------------------------------------------------|\n\n\n");


      SyncContaMessage payload = objectMapper.readValue(message, SyncContaMessage.class);

      SyncContaInputData inputData = new SyncContaInputData(payload.getConta(), payload.getMovimentacao());

      syncContaInputPort.execute(inputData);

      System.out.println("Bancos de dados sincronizados com sucesso!");

    }catch (Exception e){

      throw new AmqpRejectAndDontRequeueException(e);
    }


  }

}
