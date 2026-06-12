package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishContaRefusedEventOutputPort;
import br.ufpr.model.message.NotifyClienteMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishContaRefusedEventAdapter implements PublishContaRefusedEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String clienteId, String motivo){

    try{

      NotifyClienteMessage transferClienteIdSagaMessage = new NotifyClienteMessage(clienteId, motivo);

      String message = objectMapper.writeValueAsString(transferClienteIdSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CONTA_REJEITADA_EVENTO,
        message
      );

    }catch (JsonProcessingException e){

      throw new RuntimeException(e);

    }

  }
}
