package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfigMsCliente;
import br.ufpr.core.ports.output.PublishUpdateUserEmailEventOutputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
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
  public void publish(String userId, String userEmail ) {

    UpdateUserEmailMessage updateUserEmailMessage = new UpdateUserEmailMessage();

    updateUserEmailMessage.setUserId(userId);
    updateUserEmailMessage.setUserEmail(userEmail);

    try {
      String message = objectMapper.writeValueAsString(updateUserEmailMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConfigMsCliente.UPDATE_USER_EMAIL_EXCHANGE,
        "fluxo.atualizar-login-usuario.key",
        message
      );

      System.out.println("Email atualizado e evento publicado");
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }
  }
}
