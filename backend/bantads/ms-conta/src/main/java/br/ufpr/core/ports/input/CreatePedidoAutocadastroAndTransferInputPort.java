package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteDataInputData;

public interface CreatePedidoAutocadastroAndTransferInputPort {

  void execute(TransferClienteDataInputData inputData);
}
