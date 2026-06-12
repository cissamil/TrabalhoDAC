package br.ufpr.dataprovider.adapter;

import java.util.List;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContasByGerenteAndStatusOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import br.ufpr.core.domain.StatusConta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// MS-CONTA

@Component
@RequiredArgsConstructor
public class FindContasByGerenteAndStatusAdapter implements FindContasByGerenteAndStatusOutputPort {

  private final ContaCommandRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public List<Conta> find(String gerenteId, StatusConta statusConta) {

    List<ContaCommandEntity> entities;

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
