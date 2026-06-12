package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.output.RemoveClienteOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveClienteAdapter implements RemoveClienteOutputPort {

  private final ClienteRepository repository;
  private final ClienteEntityMapper mapper;

  @Override
  public void remove(Cliente cliente) {

    ClienteEntity entity = mapper.toEntity(cliente);

    repository.delete(entity);
  }
}
