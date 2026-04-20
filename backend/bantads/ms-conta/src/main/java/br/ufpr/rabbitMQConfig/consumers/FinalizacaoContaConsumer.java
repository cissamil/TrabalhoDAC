package br.ufpr.rabbitMQConfig.consumers;

import br.ufpr.dataprovider.adapter.ContaEntity;
import br.ufpr.dataprovider.adapter.PedidoSagaDTO;
import br.ufpr.dataprovider.adapter.StatusPedido;
import br.ufpr.dataprovider.adapter.UsuarioSagaDTO;
import br.ufpr.dataprovider.client.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FinalizacaoContaConsumer {

  private final ContaRepository contaRepository;
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = "pedido.finalizado.queue")
  public void finalizarCriacao(PedidoSagaDTO pedidoFinalizado){
    if(!StatusPedido.APROVADO.equals(pedidoFinalizado.getStatusPedido())){
      throw new  RuntimeException("Pedido não aprovado");
    }

    ContaEntity novaConta = new ContaEntity();

    novaConta.setCpfCliente(pedidoFinalizado.getCpfCliente());
    novaConta.setNomeCliente(pedidoFinalizado.getNomeCliente());
    novaConta.setNomeGerente(pedidoFinalizado.getNomeGerente());
    novaConta.setCpfGerente(pedidoFinalizado.getCpfGerente());

    novaConta.setNumeroConta(new Random().nextInt(9000) + 1000);
    novaConta.setSaldo(BigDecimal.ZERO);

    BigDecimal minimumSalaryAllowedValue = new BigDecimal(2000);
    BigDecimal salario = pedidoFinalizado.getSalario();

    if(salario.compareTo(minimumSalaryAllowedValue) > 0){
      BigDecimal limite = salario.divide(new BigDecimal(2));
      novaConta.setLimite(limite);
    }else {
      novaConta.setLimite(new BigDecimal(0));
    }

    novaConta.setDataCriacao(new Date());

    final UsuarioSagaDTO usuarioSagaDTO = new UsuarioSagaDTO();

    usuarioSagaDTO.setEmail(pedidoFinalizado.getEmailCliente());
    usuarioSagaDTO.setCpf(pedidoFinalizado.getCpfCliente());
    usuarioSagaDTO.setStatusPedido(pedidoFinalizado.getStatusPedido());

    contaRepository.save(novaConta);

    rabbitTemplate.convertAndSend("saga.autocadastro", "criar.credencial.queue", usuarioSagaDTO);


  }
}
