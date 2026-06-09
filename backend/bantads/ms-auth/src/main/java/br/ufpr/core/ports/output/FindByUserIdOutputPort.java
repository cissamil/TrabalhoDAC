package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Usuario;

public interface FindByUserIdOutputPort {

  Usuario find(String userId);
  boolean exists(String userId);
}
