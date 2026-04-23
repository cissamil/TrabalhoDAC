package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;

public interface SaveClienteInputPort {
  void execute(Cliente cliente);
}
