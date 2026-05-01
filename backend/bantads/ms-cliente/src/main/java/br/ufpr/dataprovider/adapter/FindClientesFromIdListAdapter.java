package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.FindClientesFromIdListOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.client.ClienteRepository;
import br.ufpr.dataprovider.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindClientesFromIdListAdapter implements FindClientesFromIdListOutputPort {

  private final ClienteEntityMapper mapper;
  private final ClienteRepository repository;

  @Override
  public List<Cliente> find(List<String> clienteIds) {

    System.out.println("Adapter acionado");

    try{
      List<ClienteEntity> entities = repository.findByClienteIdIn(clienteIds);

      List<Cliente> clientes = entities.stream().map(mapper::toDomain).toList();

      return clientes;

    }catch (Exception e){
      throw new RuntimeException("Erro ao pegar clientes: " + e);
    }

  }
}
