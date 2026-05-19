package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

public interface FindGerenteByCpfOutputPort {

  Gerente find(String cpf);
  boolean exists(String cpf);
}
