package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishUserCredentialDeletedEventOutputPort;
import br.ufpr.model.message.TransferGerenteIdMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishUserCredentialDeletedEventAdapter implements PublishUserCredentialDeletedEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String userId) {

    try{
      TransferGerenteIdMessage transferGerenteIdMessage = new TransferGerenteIdMessage(userId);

      String message = objectMapper.writeValueAsString(transferGerenteIdMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_GERENTE_CREDENCIAL_DELETADA_EVENTO,
        message
      );

      System.out.println("Credencial deletada e evento publicado");

    }catch (JsonProcessingException e){
      throw new RuntimeException("Erro ao publicar evento: " + e);
    }
  }
}
