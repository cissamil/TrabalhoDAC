package br.ufpr.core.usecases;

import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreatePedidoAutocadastroAndTransferInputPort;
import br.ufpr.core.ports.output.FindGerenteWithFewerClientesOutputPort;
import br.ufpr.model.message.StatusPedido;
import br.ufpr.model.message.TransferPedidoAutocadastroSagaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePedidoAutocadastroAndTransferUseCase implements CreatePedidoAutocadastroAndTransferInputPort {

  private final FindGerenteWithFewerClientesOutputPort findGerenteWithFewerClientesOutputPort;

  public void execute(TransferClienteDataInputData inputData){

    TransferPedidoAutocadastroSagaMessage transferPedidoAutocadastroSagaMessage = new TransferPedidoAutocadastroSagaMessage();

    String gerenteId = findGerenteWithFewerClientesOutputPort.find();

    transferPedidoAutocadastroSagaMessage.setClienteId(inputData.getClienteId());
    transferPedidoAutocadastroSagaMessage.setSalario(inputData.getSalario());
    transferPedidoAutocadastroSagaMessage.setGerenteId(gerenteId);
    transferPedidoAutocadastroSagaMessage.setStatusPedido(StatusPedido.PENDENTE);




  }
}
