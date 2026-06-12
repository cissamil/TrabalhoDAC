package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishGerenteCredencialGeradaEventOutputPort;
import br.ufpr.model.message.AssignGerenteToContaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishGerenteCredencialGeradaEventAdapter implements PublishGerenteCredencialGeradaEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String gerenteId) {

    try{

      AssignGerenteToContaMessage assignGerenteToContaMessage = new AssignGerenteToContaMessage(gerenteId);

      String message = objectMapper.writeValueAsString(assignGerenteToContaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_GERENTE_CREDENCIAL_GERADA_EVENTO,
        message
      );

      System.out.println("Credencial gerada e evento publicado");

    }catch (JsonProcessingException e){
      throw new RuntimeException("Erro ao publicar mensagem: " + e);
    }
  }
}
