package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.SendSagaPortOut;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendSagaAdapter implements SendSagaPortOut{

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(Cliente cliente){
    rabbitTemplate.convertAndSend(
      RabbitMQConfig.SAGA_EXCHANGE,
      "autocadastro.key",
      cliente
    );
  }

}
