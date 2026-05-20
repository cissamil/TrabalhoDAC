package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfigMsGerente;
import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.PublishAssignContaToGerenteEventOutputPort;
import br.ufpr.model.message.AssignGerenteToContaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishAssignContaToGerenteEventAdapter implements PublishAssignContaToGerenteEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(Gerente gerente) {

    AssignGerenteToContaMessage assignGerenteToContaMessage = new AssignGerenteToContaMessage();

    assignGerenteToContaMessage.setGerenteId(gerente.getGerenteId());

    try{

      String message = objectMapper.writeValueAsString(assignGerenteToContaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConfigMsGerente.ASSIGN_ACCOUNT_TO_NEW_MANAGER_EXCHANGE,
        "fluxo.atribuir-conta.key",
        message
      );
    }catch (JsonProcessingException e){
      throw new RuntimeException(e);
    }

  }
}
