package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Usuario;

public interface SaveUsuarioCredentialOutputPort {

  Usuario save(Usuario usuario);
}
