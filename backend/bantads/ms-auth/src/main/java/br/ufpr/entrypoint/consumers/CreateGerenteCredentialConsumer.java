package br.ufpr.entrypoint.consumers;

import lombok.RequiredArgsConstructor;
import br.ufpr.core.domain.GerenteInputData;
import org.springframework.stereotype.Component;
import br.ufpr.common.constants.RabbitMQConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufpr.model.message.TransferGerenteIdMessage;
import org.springframework.amqp.rabbit.annotation.Queue;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import br.ufpr.core.ports.input.PrepareGerenteCredentialInputPort;
import br.ufpr.model.message.GenerateGerenteCredentialEventMessage;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

@Component
@RequiredArgsConstructor
public class CreateGerenteCredentialConsumer {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  private final PrepareGerenteCredentialInputPort prepareGerenteCredentialInputPort;

  @RabbitListener(
    bindings = @QueueBinding(
      exchange = @Exchange(RabbitMQConstants.BANTADS_EXCHANGE),
      value = @Queue(RabbitMQConstants.FILA_CREDENCIAL_GERENTE_GERAR),
      key = RabbitMQConstants.RK_GERENTE_CREDENCIAL_CRIAR_COMANDO
    )
  )
  public void criarCredencial(String message) throws JsonProcessingException {
    try{

      GenerateGerenteCredentialEventMessage payload = objectMapper.readValue(message, GenerateGerenteCredentialEventMessage.class);

      GerenteInputData inputData = new GerenteInputData();

      inputData.setEmail(payload.getEmail());
      inputData.setGerenteId(payload.getGerenteId());
      inputData.setSenha(payload.getSenha());

      System.out.println("Dados do gerente recebidos: " + inputData.getGerenteId());
      System.out.println("Senha: " + inputData.getSenha());


      prepareGerenteCredentialInputPort.execute(inputData);

    } catch (Exception e){
      GenerateGerenteCredentialEventMessage payload = objectMapper.readValue(message, GenerateGerenteCredentialEventMessage.class);

      TransferGerenteIdMessage transferGerenteIdMessage = new TransferGerenteIdMessage(payload.getGerenteId());

      String rollbackMessage = objectMapper.writeValueAsString(transferGerenteIdMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_GERENTE_CREDENCIAL_GERADA_FALHA,
        rollbackMessage
      );

      throw new AmqpRejectAndDontRequeueException(e);
    }

  }
}
