package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.output.FindByUserIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import br.ufpr.dataprovider.client.UsuarioRepository;
import br.ufpr.dataprovider.mapper.UsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindByUserIdAdapter implements FindByUserIdOutputPort {

  private final UsuarioRepository repository;
  private final UsuarioEntityMapper mapper;

  @Override
  public Usuario find(String userId) {

    UsuarioEntity entity = repository.findByUserId(userId);

    return mapper.toDomain(entity);
  }
}
