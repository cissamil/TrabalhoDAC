package br.ufpr.dataprovider.adapter;

import java.util.List;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContasByGerenteAndStatusOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import br.ufpr.model.enumerator.StatusConta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// MS-CONTA

@Component
@RequiredArgsConstructor
public class FindContasByGerenteAndStatusAdapter implements FindContasByGerenteAndStatusOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public List<Conta> find(String gerenteId, StatusConta statusConta) {


    List<ContaEntity> entities = repository.findByGerenteIdAndStatusConta(gerenteId, statusConta);

    List<Conta> contas = entities.stream()
      .map(mapper::toDomain)
      .toList();

    return contas;
  }
}
