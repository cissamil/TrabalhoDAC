package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;

public interface CreateClienteInputPort {
  void execute(Cliente cliente);
}
