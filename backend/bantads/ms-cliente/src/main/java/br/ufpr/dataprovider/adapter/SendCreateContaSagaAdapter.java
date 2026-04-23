package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.SendCreateContaSagaOutputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCreateContaSagaAdapter implements SendCreateContaSagaOutputPort {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(Cliente cliente){

    TransferClienteDataSagaMessage transferClienteDataSagaMessage = new TransferClienteDataSagaMessage();

    transferClienteDataSagaMessage.setClienteId(cliente.getClienteId());
    transferClienteDataSagaMessage.setSalario(cliente.getSalario());

    rabbitTemplate.convertAndSend(
      RabbitMQConfig.SAGA_EXCHANGE,
      "autocadastro.key",
      transferClienteDataSagaMessage
    );
  }

}
