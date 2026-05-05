package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Usuario;

public interface GenerateAccessTokenOutputPort {

  String generate(Usuario usuario);
}
