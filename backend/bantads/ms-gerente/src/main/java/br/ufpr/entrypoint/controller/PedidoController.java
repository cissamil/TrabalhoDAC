package br.ufpr.entrypoint.controller;

import br.ufpr.core.mapper.PedidoCadastroMapper;
import br.ufpr.core.ports.input.AprovePedidoCadastroInputPort;
import br.ufpr.dataprovider.adapter.domain.PedidoAutocadastroEntity;
import br.ufpr.dataprovider.adapter.domain.PedidoSagaDTO;
import br.ufpr.dataprovider.client.PedidoAutocadastroRepository;
import br.ufpr.model.enumerator.StatusPedido;
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

  AprovePedidoCadastroInputPort aprovePedidoCadastroInputPort;

  @PostMapping("/{id}/aprovar")
  public ResponseEntity<Void> aprovarPedido(@PathVariable Integer id){

    aprovePedidoCadastroInputPort.execute(id);

    return ResponseEntity.accepted().build();

  }
}
