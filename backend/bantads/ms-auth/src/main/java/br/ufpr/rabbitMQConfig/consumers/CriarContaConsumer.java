package br.ufpr.rabbitMQConfig.consumers;

import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.ports.input.CreateClienteCredentialInputPort;
import br.ufpr.model.enumerator.TipoUsuario;
import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import br.ufpr.dataprovider.adapter.domain.UsuarioSagaDTO;
import br.ufpr.dataprovider.client.UsuarioRepository;
import br.ufpr.model.enumerator.StatusPedido;
import br.ufpr.model.message.TransferClienteIdSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarContaConsumer {

  private CreateClienteCredentialInputPort createClienteCredentialInputPort;

  @RabbitListener(queues = "criar.credencial.queue")
  public void criarCredencial(TransferClienteIdSagaMessage message){

    TransferClienteIdInputData inputData = new TransferClienteIdInputData();
    inputData.setClienteId(message.getClienteId());

    createClienteCredentialInputPort.execute(inputData);


  }
}
