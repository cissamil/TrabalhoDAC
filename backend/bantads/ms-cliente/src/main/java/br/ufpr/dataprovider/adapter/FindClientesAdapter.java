package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.FindClientesOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindClientesAdapter implements FindClientesOutputPort{

  private final ClienteRepository repository;
  private final ClienteEntityMapper mapper;

  @Override
  public List<Cliente> find() {

    List<ClienteEntity> entities = repository.findAll();

    List<Cliente> clientes = entities.stream().map(mapper::toDomain).toList();

    return clientes;
  }
}
