package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Usuario;

public interface FindByEmailOutputPort {

  Usuario find(String email);
}
