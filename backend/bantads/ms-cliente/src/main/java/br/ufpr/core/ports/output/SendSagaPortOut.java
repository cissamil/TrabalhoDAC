package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface SendSagaPortOut {
  void send(Cliente cliente);
}
