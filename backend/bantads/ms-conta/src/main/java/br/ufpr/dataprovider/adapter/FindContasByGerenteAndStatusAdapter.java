package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.core.ports.output.FindContasByGerenteAndStatusOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-CONTA

@Component
@RequiredArgsConstructor
public class FindContasByGerenteAndStatusAdapter implements FindContasByGerenteAndStatusOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public List<Conta> find(String gerenteId, StatusConta statusConta) {

    List<ContaQueryEntity> entities;

    if(gerenteId.equals(" ") || gerenteId.isEmpty()){

      System.out.println("Id do gerente vazio, pegando contas aprovadas gerais");
      entities = repository.findByStatusConta(statusConta);
    } else{

      entities = repository.findByGerenteIdAndStatusConta(gerenteId, statusConta);
    }

    System.out.println("Contas aprovadas encontradas: " + entities.size());

    return entities.stream()
      .map(mapper::toDomain)
      .toList();
  }
}
