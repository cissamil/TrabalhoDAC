package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.SendEmailInputData;
import br.ufpr.core.ports.SendEmailInputPort;
import br.ufpr.model.message.SendEmailMessage;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendBadNewsEmailCommandConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final SendEmailInputPort sendEmailInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_EMAIL_ENVIAR_MAS_NOTICIAS),
      key = RabbitMQConstants.RK_EMAIL_ENVIAR_MAS_NOTICIAS_COMANDO
    )
  )
  public void sendEmailCommand(String message) throws JsonProcessingException {

    try{
      System.out.println("Comando de email recebido.");

      SendEmailMessage payload = objectMapper.readValue(message, SendEmailMessage.class);

      SendEmailInputData inputData = new SendEmailInputData();

      inputData.setReceiver(payload.getReceiver());
      inputData.setSubject(payload.getSubject());
      inputData.setMessage(payload.getMessage());


      sendEmailInputPort.send(inputData);

    } catch (Exception e){

      SendEmailMessage payload = objectMapper.readValue(message, SendEmailMessage.class);

      TransferClienteIdSagaMessage transferClienteIdSagaMessage = new TransferClienteIdSagaMessage(payload.getReceiverId());

      String rollbackMessage = objectMapper.writeValueAsString(transferClienteIdSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_EMAIL_MAS_NOTICIAS_FALHA_EVENTO,
        rollbackMessage
      );

      throw new AmqpRejectAndDontRequeueException("Erro ao enviar email: " + e);
    }
  }
}
