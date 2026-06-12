package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.config.RabbitMQConfigMsCliente;
import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.PublishCreatedClienteAccountEventOutputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishCreatedClienteAccountEventAdapter implements PublishCreatedClienteAccountEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(Cliente cliente){

    TransferClienteDataSagaMessage transferClienteDataSagaMessage = new TransferClienteDataSagaMessage();

    transferClienteDataSagaMessage.setClienteId(cliente.getClienteId());
    transferClienteDataSagaMessage.setSalario(cliente.getSalario());

    try {
      String message = objectMapper.writeValueAsString(transferClienteDataSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CLIENTE_CRIADO_SUCESSO,
        message
      );

      System.out.println("Cliente cadastrado e evento publicado");
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }

  }

}
