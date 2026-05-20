package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

public interface PublishAssignContaToGerenteEventOutputPort {

  void publish(Gerente gerente);
}
