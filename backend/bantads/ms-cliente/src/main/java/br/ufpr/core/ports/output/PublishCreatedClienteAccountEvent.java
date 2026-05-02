package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface PublishCreatedClienteAccountEvent {
  void send(Cliente cliente);
}
