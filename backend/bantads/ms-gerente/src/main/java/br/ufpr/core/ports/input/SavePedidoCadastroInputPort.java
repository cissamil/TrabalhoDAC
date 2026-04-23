package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferPedidoCadastroInputData;

public interface SavePedidoCadastroInputPort {

  void save(TransferPedidoCadastroInputData inputData);

}
