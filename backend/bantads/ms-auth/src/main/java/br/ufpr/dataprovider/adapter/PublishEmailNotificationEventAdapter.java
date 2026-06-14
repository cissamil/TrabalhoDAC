package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishEmailNotificationEventOutputPort;
import br.ufpr.model.message.SendEmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishEmailNotificationEventAdapter implements PublishEmailNotificationEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String email, String userId, String content, String subject){

    try{

      String emailSubject = buildEmailSubject(subject);

      SendEmailMessage sendEmailMessage = new SendEmailMessage();

      sendEmailMessage.setReceiver(email);
      sendEmailMessage.setMessage(content);
      sendEmailMessage.setReceiverId(userId);
      sendEmailMessage.setSubject(emailSubject);

      String message = objectMapper.writeValueAsString(sendEmailMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_NOTIFICACAO_PRONTA_EVENTO,
        message
      );

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }

  }

  private String buildEmailSubject(String subject) {

    if(!subject.isBlank()){
      return subject;
    }

    return "Há novas informações sobre a sua conta!";
  }
}
