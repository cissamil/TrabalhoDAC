package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreatePedidoAutocadastroAndTransferInputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final CreatePedidoAutocadastroAndTransferInputPort createPedidoAutocadastroAndTransferInputPort;

  @RabbitListener(queues = RabbitMQConfig.SAGA_QUEUE_AUTOCADASTRO)
  public void receivePedido(TransferClienteDataSagaMessage message){

    TransferClienteDataInputData inputData = new TransferClienteDataInputData();

    inputData.setClienteId(message.getClienteId());
    inputData.setSalario(message.getSalario());

    createPedidoAutocadastroAndTransferInputPort.execute(inputData);

  }
}
