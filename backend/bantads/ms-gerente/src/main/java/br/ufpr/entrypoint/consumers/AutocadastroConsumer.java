package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.domain.TransferPedidoCadastroInputData;
import br.ufpr.core.ports.input.SavePedidoCadastroInputPort;
import br.ufpr.model.message.TransferPedidoAutocadastroSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroConsumer {

  private final RabbitTemplate rabbitTemplate;

  private final SavePedidoCadastroInputPort savePedidoCadastroInputPort;

  @RabbitListener(queues = RabbitMQConfig.SAGA_QUEUE_PEDIDO_CRIADO)
  public void receivePedido(TransferPedidoAutocadastroSagaMessage message){

    TransferPedidoCadastroInputData inputData = new TransferPedidoCadastroInputData();

    inputData.setClienteId(message.getClienteId());
    inputData.setGerenteId(message.getGerenteId());
    inputData.setSalario(message.getSalario());
    inputData.setStatusPedido(message.getStatusPedido());

    savePedidoCadastroInputPort.save(inputData);

  }
}
