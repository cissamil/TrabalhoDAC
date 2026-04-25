package br.ufpr.core.usecases;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.core.ports.input.AprovePedidoCadastroInputPort;
import br.ufpr.core.ports.output.FindPedidoCadastroByIdOutputPort;
import br.ufpr.core.ports.output.SavePedidoCadastroOutputPort;
import br.ufpr.model.enumerator.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AprovePedidoCadatroUsecase implements AprovePedidoCadastroInputPort {

  private final FindPedidoCadastroByIdOutputPort findPedidoCadastroByIdOutputPort;
  private final SavePedidoCadastroOutputPort savePedidoCadastroOutputPort;
  private final RabbitTemplate rabbitTemplate;
  @Override
  public void execute(Integer id) {
    PedidoCadastro pedidoCadastro = findPedidoCadastroByIdOutputPort.find(id);
    validatePedidoCadastro(pedidoCadastro);

    pedidoCadastro.setStatusPedido(StatusPedido.APROVADO);

    savePedidoCadastroOutputPort.save(pedidoCadastro);


//    rabbitTemplate.convertAndSend("saga.autocadastro", "pedido.aprovado", pedidoSagaDTO);


  }

  private static void validatePedidoCadastro(PedidoCadastro pedidoCadastro) {
    if(pedidoCadastro == null){
      throw new RuntimeException("Pedido não encontrado");
    }

    if(pedidoCadastro.getStatusPedido().equals(StatusPedido.APROVADO)){
      throw new RuntimeException("Pedido já aprovado");
    }

    if(pedidoCadastro.getStatusPedido().equals(StatusPedido.RECUSADO)){
      throw new RuntimeException("Pedido já recusado");
    }
  }
}
