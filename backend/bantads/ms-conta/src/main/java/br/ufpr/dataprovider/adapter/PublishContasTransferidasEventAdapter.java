package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.ApprovedContaEvent;
import br.ufpr.core.ports.output.PublishContasTransferidasEventOutputPort;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import br.ufpr.model.message.TransferGerenteIdMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PublishContasTransferidasEventAdapter implements PublishContasTransferidasEventOutputPort {


  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String gerenteId) {

    TransferGerenteIdMessage transferClienteIdSagaMessage = new TransferGerenteIdMessage(gerenteId);

    try{
      String message = objectMapper.writeValueAsString(transferClienteIdSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CONTAS_TRANSFERIDAS_SUCESSO,
        message
      );

      System.out.println("Contas transferidas e evento publicado");

    } catch (JsonProcessingException e){
      throw  new RuntimeException(e);
    }
  }
}
