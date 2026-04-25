package br.ufpr.dataprovider.adapter;

import br.ufpr.config.RabbitMQConfigMsCliente;
import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.SendCreateContaSagaOutputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendCreateContaSagaAdapter implements SendCreateContaSagaOutputPort {

  @Autowired
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void send(Cliente cliente){

    //@TODO TRANSFORMAR A MENSAGEM EM UMA STRING ANTES DE PUBLICAR

    TransferClienteDataSagaMessage transferClienteDataSagaMessage = new TransferClienteDataSagaMessage();

    transferClienteDataSagaMessage.setClienteId(cliente.getClienteId());
    transferClienteDataSagaMessage.setSalario(cliente.getSalario());

    try {
      String message = objectMapper.writeValueAsString(transferClienteDataSagaMessage);

      rabbitTemplate.convertAndSend(
        RabbitMQConfigMsCliente.REGISTER_EXCHANGE,
        "fluxo.autocadastro.key",
        message
      );

      System.out.println("[MS-CLIENTE] Cadastro de cliente criado e atributos enviados para MS-CONTA");
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

}
