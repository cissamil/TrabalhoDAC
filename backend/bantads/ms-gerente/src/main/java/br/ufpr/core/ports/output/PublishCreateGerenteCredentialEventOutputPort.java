package br.ufpr.core.ports.output;

import br.ufpr.core.domain.GerenteEventPublisher;

public interface PublishCreateGerenteCredentialEventOutputPort {

  void publish(GerenteEventPublisher publisher);
}
