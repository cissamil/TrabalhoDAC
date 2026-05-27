package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfigMsGerente;
import br.ufpr.core.ports.output.PublishCreateGerenteCredentialEventOutputPort;
import br.ufpr.core.domain.GerenteEventPublisher;
import br.ufpr.model.message.GenerateGerenteCredentialEventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishCreateGerenteCredentialEventAdapter implements PublishCreateGerenteCredentialEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(GerenteEventPublisher publisher) {

    System.out.println("Senha a ser enviada: " + publisher.getSenha());
    GenerateGerenteCredentialEventMessage generateGerenteCredentialEventMessage = new GenerateGerenteCredentialEventMessage();

    generateGerenteCredentialEventMessage.setGerenteId(publisher.getGerenteId());
    generateGerenteCredentialEventMessage.setEmail(publisher.getEmail());
    generateGerenteCredentialEventMessage.setSenha(publisher.getSenha());

    try{

      String message = objectMapper.writeValueAsString(generateGerenteCredentialEventMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConfigMsGerente.GENERATE_MANAGER_CREDENTIAL_EXCHANGE,
        "fluxo.gerar-credencial-gerente.key",
        message
      );

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }
}
