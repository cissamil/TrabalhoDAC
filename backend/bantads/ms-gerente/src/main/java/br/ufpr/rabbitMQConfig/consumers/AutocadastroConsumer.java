package br.ufpr.rabbitMQConfig.consumers;

import br.ufpr.dataprovider.adapter.PedidoAutocadastroEntity;
import br.ufpr.dataprovider.adapter.PedidoSagaDTO;
import br.ufpr.dataprovider.client.PedidoAutocadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final PedidoAutocadastroRepository repository;

  @RabbitListener(queues = "gerente.analisar")
  public void receivePedido(PedidoSagaDTO pedido){
    System.out.println("Pedido de autocadastro recebido do microsserviço de conta");

    System.out.println("Salvando o pedido no banco de dados");

    final PedidoAutocadastroEntity entity = new PedidoAutocadastroEntity();

    entity.setCpfGerente(pedido.getCpfGerente());
    entity.setNomeGerente(pedido.getNomeGerente());
    entity.setNomeCliente(pedido.getNomeCliente());
    entity.setSalario(pedido.getSalario());
    entity.setCpfCliente(pedido.getCpfCliente());
    entity.setStatusPedido(pedido.getStatusPedido());
    entity.setEmailCliente(pedido.getEmailCliente());

    repository.save(entity);

  }
}
