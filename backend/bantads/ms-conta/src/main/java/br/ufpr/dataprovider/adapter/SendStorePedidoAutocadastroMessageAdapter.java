package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.ports.output.SendStorePedidoAutocadastroMessageOutputPort;
import br.ufpr.model.message.TransferPedidoAutocadastroSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendStorePedidoAutocadastroMessageAdapter implements SendStorePedidoAutocadastroMessageOutputPort {

  public RabbitTemplate rabbitTemplate;

  public void send(TransferPedidoAutocadastroSagaMessage message){

    rabbitTemplate.convertAndSend(
      RabbitMQConfig.SAGA_EXCHANGE,
      "pedido.criado.key",
      message
    );


  }
}
