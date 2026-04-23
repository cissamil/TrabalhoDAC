package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface FindClienteByClienteIdOutputPort {

  Cliente find(String clienteId);
  boolean exists(String clienteId);



}
