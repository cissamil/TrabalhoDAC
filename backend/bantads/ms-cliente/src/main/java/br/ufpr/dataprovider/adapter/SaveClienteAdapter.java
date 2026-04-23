package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.SaveClienteOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveClienteAdapter implements SaveClienteOutputPort {

  private final ClienteEntityMapper mapper;
  private final ClienteRepository repository;

  @Override
  public Cliente save(Cliente cliente){

    ClienteEntity entity = mapper.toEntity(cliente);

    ClienteEntity savedEntity = repository.save(entity);

    return mapper.toDomain(savedEntity);
  }

}
