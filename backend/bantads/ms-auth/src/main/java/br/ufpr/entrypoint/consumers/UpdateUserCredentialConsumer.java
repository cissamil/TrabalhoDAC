package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.UpdateUserEmailInputData;
import br.ufpr.core.ports.input.UpdateUserEmailInputPort;
import br.ufpr.model.message.UpdateUserEmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserCredentialConsumer {

  @Autowired
  private final RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper;
  private final UpdateUserEmailInputPort updateUserEmailInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CREDENCIAL_CLIENTE_ATUALIZAR),
      key = RabbitMQConstants.RK_CLIENTE_CREDENCIAL_ATUALIZAR_COMANDO
    )
  )
  public void updateUserEmail(String message) throws JsonProcessingException {
    try{

      System.out.println("Comando 'atualizar credencial cliente' recebido com sucesso. Executando tarefa...");

      UpdateUserEmailMessage payload = objectMapper.readValue(message, UpdateUserEmailMessage.class);

      UpdateUserEmailInputData inputData = new UpdateUserEmailInputData();


      inputData.setUserId(payload.getUserId());
      inputData.setUserNewEmail(payload.getUserNewEmail());
      inputData.setUserPreviousEmail(payload.getUserPreviousEmail());

      updateUserEmailInputPort.execute(inputData);

      System.out.println("Credencial do cliente atualizada com sucesso!");


    } catch (Exception e){

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CLIENTE_CREDENCIAL_ATUALIZADA_FALHA,
        message
      );

      throw new AmqpRejectAndDontRequeueException(e);
    }
  }
}
