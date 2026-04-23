package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfig;
import br.ufpr.core.domain.TransferContaCreationDataInputData;
import br.ufpr.core.ports.input.CreateContaInputPort;
import br.ufpr.model.message.TransferContaCreationDataSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalizacaoContaConsumer {

  private final CreateContaInputPort createContaInputPort;
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConfig.SAGA_QUEUE_PEDIDO_APROVADO)
  public void finalizarCriacao(TransferContaCreationDataSagaMessage message){

    TransferContaCreationDataInputData inputData = new TransferContaCreationDataInputData();

    inputData.setSalario(message.getSalario());
    inputData.setClienteId(message.getClienteId());
    inputData.setGerenteId(message.getGerenteId());

    createContaInputPort.execute(inputData);

    // @TODO MENSAGERIA PARA MS-AUTH PARA CRIAÇÃO DE CREDENCIAL DO CLIENTE
    // rabbitTemplate.convertAndSend("saga.autocadastro", "criar.credencial.queue", usuarioSagaDTO);


  }
}
