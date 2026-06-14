package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.input.PrepareClienteNotificationInputPort;
import br.ufpr.core.ports.input.RevertClienteEmailInputPort;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevertClienteCredentialConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final RevertClienteEmailInputPort revertClienteEmailInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CLIENTE_EMAIL_REVERTER),
      key = RabbitMQConstants.RK_CLIENTE_EMAIL_REVERTER_COMANDO
    )
  )
  public void notifyClienteConsumer(String message) throws JsonProcessingException {

      try {

        System.out.println("Comando de reversão de email recebido. Executando...");

        UpdateUserEmailMessage payload = objectMapper.readValue(message, UpdateUserEmailMessage.class);

        String clienteId = payload.getUserId();
        String previousEmail = payload.getUserPreviousEmail();

        revertClienteEmailInputPort.execute(clienteId, previousEmail);

        System.out.println("Reversão concluída com sucesso!");

      } catch (Exception e) {

        throw new AmqpRejectAndDontRequeueException(e);
      }
    }
  }


