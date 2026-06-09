package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.ports.input.DeleteUserCredentialInputPort;
import br.ufpr.core.ports.input.PrepareGerenteCredentialInputPort;
import br.ufpr.model.message.GenerateGerenteCredentialEventMessage;
import br.ufpr.model.message.TransferGerenteIdMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteGerenteCredentialConsumer {


  private final ObjectMapper objectMapper;
  private final DeleteUserCredentialInputPort deleteUserCredentialInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CREDENCIAL_GERENTE_REMOVER),
      key = RabbitMQConstants.RK_GERENTE_CREDENCIAL_REMOVER_COMANDO
    )
  )
  public void deleteCredential(String message) throws JsonProcessingException {

    System.out.println("Comando de deleção de credencial recebido.");

    try{

      TransferGerenteIdMessage payload = objectMapper.readValue(message, TransferGerenteIdMessage.class);

      String userId = payload.getGerenteId();

      deleteUserCredentialInputPort.delete(userId);

    } catch (Exception e){
      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
