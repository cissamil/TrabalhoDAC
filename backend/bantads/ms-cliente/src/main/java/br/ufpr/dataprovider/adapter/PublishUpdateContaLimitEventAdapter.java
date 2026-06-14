package br.ufpr.dataprovider.adapter;

import br.ufpr.common.constants.RabbitMQConstants;
import br.ufpr.core.ports.output.PublishUpdateContaLimitEventOutputPort;
import br.ufpr.model.message.UpdateContaLimitMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PublishUpdateContaLimitEventAdapter implements PublishUpdateContaLimitEventOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(String clienteId, BigDecimal clienteSalary) {

    UpdateContaLimitMessage updateContaLimitMessage = new UpdateContaLimitMessage();

    updateContaLimitMessage.setClienteId(clienteId);
    updateContaLimitMessage.setClienteSalary(clienteSalary);

    try {
      String message = objectMapper.writeValueAsString(updateContaLimitMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConstants.BANTADS_EXCHANGE,
        RabbitMQConstants.RK_CLIENTE_SALARIO_ATUALIZADO_EVENTO,
        message
      );

      System.out.println("Salário atualizado e evento publicado");
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erro ao publicar mensagem" + e);

    }

  }
}
