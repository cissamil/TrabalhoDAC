package br.ufpr.entrypoint.consumers;

import br.ufpr.config.RabbitMQConfigMsConta;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreatePendingContaInputPort;
import br.ufpr.model.message.TransferClienteDataSagaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutocadastroConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    private final RabbitTemplate rabbitTemplate;
    private final CreatePendingContaInputPort createPendingContaInputPort;

    @RabbitListener(queues = RabbitMQConfigMsConta.REGISTER_QUEUE)
    public void receivePedido(String message) throws JsonProcessingException {

      try {

        TransferClienteDataSagaMessage payload = objectMapper.readValue(message, TransferClienteDataSagaMessage.class);

        System.out.println("[MS-CONTA] Cadastro de cliente recebido, criando conta com dados: Id: " + payload.getClienteId() + ", Salário: " + payload.getSalario());

        TransferClienteDataInputData inputData = new TransferClienteDataInputData();

        inputData.setClienteId(payload.getClienteId());
        inputData.setSalario(payload.getSalario());

        createPendingContaInputPort.execute(inputData);

      } catch (Exception e) {
        throw new AmqpRejectAndDontRequeueException(e);
      }

    }


  //  @RabbitListener(queues = RabbitMQConfigMsConta.REGISTER_QUEUE)
  //  public void receivePedido(String message) throws JsonProcessingException {
  //
  //    try {
  //
  //      TransferClienteDataSagaMessage payload = objectMapper.readValue(message, TransferClienteDataSagaMessage.class);
  //
  //      System.out.println("[MS-CONTA] Cadastro de cliente recebido, criando pedido com dados: Id: " + payload.getClienteId());
  //
  //      TransferClienteDataInputData inputData = new TransferClienteDataInputData();
  //
  //      inputData.setClienteId(payload.getClienteId());
  //
  //      createPedidoInputPort.execute(inputData);
  //
  //    } catch (Exception e) {
  //      throw new AmqpRejectAndDontRequeueException(e);
  //    }
  //
  //  }
}
