package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Usuario;
import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

  public Usuario toDomain(UsuarioEntity entity){
    if(entity == null) return null;

    Usuario usuario = new Usuario();

    usuario.setId(entity.getId());
    usuario.setLogin(entity.getLogin());
    usuario.setSenha(entity.getSenha());
    usuario.setTipoUsuario(entity.getTipoUsuario());

    return usuario;
  }

  public UsuarioEntity toEntity(Usuario usuario){
    if(usuario == null) return null;

    UsuarioEntity entity = new UsuarioEntity();

    entity.setId(usuario.getId());
    entity.setLogin(usuario.getLogin());
    entity.setSenha(usuario.getSenha());
    entity.setTipoUsuario(usuario.getTipoUsuario());

    return entity;
  }
}
