package br.ufpr.core.ports.output;

import br.ufpr.core.domain.GerenteEventPublisher;

public interface PublishCreatedGerenteEventOutputPort {

  void publish(GerenteEventPublisher publisher);
}
