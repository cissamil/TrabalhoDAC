package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface PublishCreatedClienteAccountEventOutputPort {
  void publish(Cliente cliente);
}
