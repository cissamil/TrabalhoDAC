package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfigMsConta;
import br.ufpr.core.domain.ApprovedContaEvent;
import br.ufpr.core.ports.output.PublishContaAprovadaEventOutputPort;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishContaAprovadaEventAdapter implements PublishContaAprovadaEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(ApprovedContaEvent event) {

    TransferClienteIdSagaMessage transferClienteIdSagaMessage = new TransferClienteIdSagaMessage(event.getClienteId());

    try{
      String message = objectMapper.writeValueAsString(transferClienteIdSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConfigMsConta.APPROVED_ACCOUNT_EXCHANGE,
        "fluxo.conta-aprovada.key",
        message
      );

      System.out.println("Conta aprovada e evento publicado");

    } catch (JsonProcessingException e){
      throw  new RuntimeException(e);
    }
  }
}
