package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishUpdateUserEmailEventOutputPort;
import br.ufpr.model.message.UpdateUserEmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishUpdateUserEmailEventAdapter implements PublishUpdateUserEmailEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;


  @Override
  public void publish(String clienteId, String newClienteEmail, String previousClienteEmail) {

    UpdateUserEmailMessage updateUserEmailMessage = new UpdateUserEmailMessage();

    updateUserEmailMessage.setUserId(clienteId);
    updateUserEmailMessage.setUserNewEmail(newClienteEmail);
    updateUserEmailMessage.setUserPreviousEmail(previousClienteEmail);

    try {
      String message = objectMapper.writeValueAsString(updateUserEmailMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CLIENTE_EMAIL_ATUALIZADO_EVENTO,
        message
      );

      System.out.println("Email atualizado e evento publicado");
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }
  }
}
