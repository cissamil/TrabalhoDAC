package br.ufpr.rabbitMQConfig.consumers;

import br.ufpr.dataprovider.adapter.GerenteInfo;
import br.ufpr.dataprovider.adapter.PedidoSagaDTO;
import br.ufpr.dataprovider.adapter.StatusPedido;
import br.ufpr.dataprovider.client.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutocadastroConsumer {

  private final RabbitTemplate rabbitTemplate;
  private final ContaRepository contaRepository;

  @RabbitListener(queues = "saga.autocadastro.queue")
  public void receivePedido(PedidoSagaDTO pedido){
    System.out.println("Recebido o pedido de cadastro para o CPF: " + pedido.getNomeCliente());

    GerenteInfo allocatedGerente = contaRepository.getGerenteWithFewerClientes();

    System.out.println("Alocando cliente para gerente " + allocatedGerente.getNomeGerente());

    pedido.setCpfGerente(allocatedGerente.getCpfGerente());
    pedido.setNomeGerente(allocatedGerente.getNomeGerente());
    pedido.setStatusPedido(StatusPedido.PENDENTE);

    System.out.println("Enviando pedido para microsserviços de gerentes ");

    rabbitTemplate.convertAndSend("saga.autocadastro", "gerente.analisar", pedido);
  }
}
