package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;
import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import br.ufpr.dataprovider.client.UsuarioRepository;
import br.ufpr.dataprovider.mapper.UsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUsuarioCredentialAdapter implements SaveUsuarioCredentialOutputPort {

  private final UsuarioRepository repository;
  private final UsuarioEntityMapper mapper;

  @Override
  public Usuario save(Usuario usuario) {

    UsuarioEntity entity = mapper.toEntity(usuario);

    UsuarioEntity newEntity = repository.save(entity);

    return mapper.toDomain(newEntity);
  }
}
