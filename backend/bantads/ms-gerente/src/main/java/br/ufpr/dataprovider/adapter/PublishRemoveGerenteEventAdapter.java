package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishRemoveGerenteEventOutputPort;
import br.ufpr.model.message.TransferContasToGerenteMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishRemoveGerenteEventAdapter implements PublishRemoveGerenteEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String gerenteId) {

    TransferContasToGerenteMessage transferContasToGerenteMessage = new TransferContasToGerenteMessage();

    transferContasToGerenteMessage.setGerenteId(gerenteId);

    try{
      System.out.println("Publicando evento de exclusão de gerente");

      String message = objectMapper.writeValueAsString(transferContasToGerenteMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_GERENTE_REMOVER_EVENTO,
        message
      );

      System.out.println("Evento enviado com sucesso!");

    }catch (JsonProcessingException e){
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }

  }
}
