package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.output.FindByEmailOutputPort;
import br.ufpr.dataprovider.adapter.domain.UsuarioEntity;
import br.ufpr.dataprovider.client.UsuarioRepository;
import br.ufpr.dataprovider.mapper.UsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindByEmailAdapter implements FindByEmailOutputPort {

  private final UsuarioRepository repository;
  private final UsuarioEntityMapper mapper;

  @Override
  public Usuario find(String email) {

    UsuarioEntity entity = repository.findByEmail(email);

    return mapper.toDomain(entity);

  }
}
