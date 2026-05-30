package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;

public interface UpdateClienteInputPort {

  void execute(Cliente updatedClienteData);
}
