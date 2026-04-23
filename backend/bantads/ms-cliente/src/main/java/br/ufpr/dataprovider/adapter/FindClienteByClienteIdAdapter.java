package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindClienteByClienteIdAdapter implements FindClienteByClienteIdOutputPort {

  private final ClienteEntityMapper mapper;
  private final ClienteRepository repository;

  @Override
  public Cliente find(String clienteId) {

    ClienteEntity entity = repository.findByClienteId(clienteId);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String clienteId) {
    return repository.existsByClienteId(clienteId);
  }
}
