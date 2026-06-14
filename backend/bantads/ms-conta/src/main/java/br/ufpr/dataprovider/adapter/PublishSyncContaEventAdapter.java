package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.PublishSyncContaEventOutputPort;
import br.ufpr.entrypoint.request.SyncContaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishSyncContaEventAdapter implements PublishSyncContaEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(Conta conta, Movimentacao movimentacao) {

    try {

      SyncContaMessage syncContaMessage = new SyncContaMessage(conta, movimentacao);

      String message = objectMapper.writeValueAsString(syncContaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CONTA_SYNC_EVENTO,
        message
      );

    }catch (JsonProcessingException e){

      throw new RuntimeException(e);
    }


  }
}
