package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.TransferClienteDataInputData;

public interface RemoveClienteOutputPort {
  void remove(Cliente cliente);
}
