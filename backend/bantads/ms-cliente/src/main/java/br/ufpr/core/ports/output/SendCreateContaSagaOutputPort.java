package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface SendCreateContaSagaOutputPort {
  void send(Cliente cliente);
}
