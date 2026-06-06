package br.ufpr.entrypoint.consumers;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.config.RabbitMQConfigMsAuth;
import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.ports.input.PrepareClienteCredentialInputPort;
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

import java.security.Key;

@Component
@RequiredArgsConstructor
public class CreateClienteCredentialConsumer {

  private final ObjectMapper objectMapper;
  private final RabbitTemplate rabbitTemplate;
  private final PrepareClienteCredentialInputPort prepareClienteCredentialInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CREDENCIAL_CLIENTE_GERAR),
      key = RabbitMQConstants.RK_CLIENTE_CREDENCIAL_CRIAR_COMANDO
    )
  )
  public void criarCredencial(String message) throws JsonProcessingException {

    try{

      TransferClienteIdSagaMessage payload = objectMapper.readValue(message, TransferClienteIdSagaMessage.class);

      TransferClienteIdInputData inputData = new TransferClienteIdInputData();
      inputData.setClienteId(payload.getClienteId());

      System.out.println("Id de cliente recebido. Gerando conta. Id: " + inputData.getClienteId());

      prepareClienteCredentialInputPort.execute(inputData);

    } catch (Exception e){

      System.out.println("Erro ao gerar credencial. Enviando fallback");

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CLIENTE_CREDENCIAL_GERADA_FALHA,
        message
      );

      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
