package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.FindClienteByCpfOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindClienteByCpfAdapter implements FindClienteByCpfOutputPort {

  private final ClienteRepository repository;
  private final ClienteEntityMapper mapper;

  @Override
  public Cliente find(String cpf) {

    ClienteEntity entity = repository.findByCpf(cpf);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String cpf) {
    return repository.existsByCpf(cpf);
  }
}
