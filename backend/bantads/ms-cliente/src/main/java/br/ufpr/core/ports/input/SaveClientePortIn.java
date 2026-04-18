package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;

public interface SaveClientePortIn {
  void execute(Cliente cliente);
}
