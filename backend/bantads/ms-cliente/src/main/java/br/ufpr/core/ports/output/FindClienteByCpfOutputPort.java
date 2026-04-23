package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface FindClienteByCpfOutputPort {

  Cliente find(String cpf);
  boolean exists(String cpf);
}
