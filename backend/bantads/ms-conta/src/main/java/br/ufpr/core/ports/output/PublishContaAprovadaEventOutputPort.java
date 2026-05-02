package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ApprovedContaEvent;

public interface PublishContaAprovadaEventOutputPort {

  void publish(ApprovedContaEvent event);

}
