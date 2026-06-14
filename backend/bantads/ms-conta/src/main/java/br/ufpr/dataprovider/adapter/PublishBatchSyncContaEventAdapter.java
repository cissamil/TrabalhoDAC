package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.PublishBatchSyncContaEventOutputPort;
import br.ufpr.entrypoint.request.BatchSyncContaMessage;
import br.ufpr.entrypoint.request.SyncContaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PublishBatchSyncContaEventAdapter implements PublishBatchSyncContaEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(List<Conta> contas) {

    try {

      BatchSyncContaMessage batchSyncContaMessage = new BatchSyncContaMessage(contas);

      String message = objectMapper.writeValueAsString(batchSyncContaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_BATCH_CONTA_SYNC_EVENTO,
        message
      );

    }catch (JsonProcessingException e){

      throw new RuntimeException(e);
    }


  }
}
