package br.ufpr.entrypoint.controller;

import br.ufpr.dataprovider.adapter.PedidoAutocadastroEntity;
import br.ufpr.dataprovider.adapter.PedidoSagaDTO;
import br.ufpr.dataprovider.adapter.StatusPedido;
import br.ufpr.dataprovider.client.PedidoAutocadastroRepository;
import br.ufpr.entrypoint.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gerentes/pedidos")
@RequiredArgsConstructor
public class PedidoController {

  private final PedidoAutocadastroRepository repository;
  private final RabbitTemplate rabbitTemplate;
  private final PedidoMapper pedidoMapper;

  @PostMapping("/{id}/aprovar")
  public ResponseEntity<Void> aprovarPedido(@PathVariable Integer id){
    PedidoAutocadastroEntity pedido = repository.findById(id)
      .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

    pedido.setStatusPedido((StatusPedido.APROVADO));

    final PedidoSagaDTO pedidoSagaDTO = pedidoMapper.toDTO(pedido);

    repository.save(pedido);

    rabbitTemplate.convertAndSend("saga.autocadastro", "pedido.finalizado", pedidoSagaDTO);

    return ResponseEntity.accepted().build();

  }
}
