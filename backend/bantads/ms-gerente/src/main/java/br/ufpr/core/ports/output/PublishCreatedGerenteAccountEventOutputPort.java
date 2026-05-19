package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

public interface PublishCreatedGerenteAccountEventOutputPort {

  void publish(Gerente gerente);
}
