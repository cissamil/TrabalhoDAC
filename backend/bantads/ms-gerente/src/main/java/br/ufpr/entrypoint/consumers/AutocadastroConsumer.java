package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.model.message.TransferPedidoAutocadastroSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroConsumer {

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConfig.SAGA_QUEUE_PEDIDO_CRIADO)
  public void receivePedido(TransferPedidoAutocadastroSagaMessage message){



  }
}
