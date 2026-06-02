package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.FindGerentesFromIdListOutputPort;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.dataprovider.client.GerenteRepository;
import br.ufpr.dataprovider.mapper.GerenteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerentesFromIdListAdapter implements FindGerentesFromIdListOutputPort {

  private final GerenteEntityMapper mapper;
  private final GerenteRepository repository;

  @Override
  public List<Gerente> find(List<String> gerenteIds) {

    try{
      List<GerenteEntity> entities = repository.findByGerenteIdIn(gerenteIds);

      List<Gerente> gerentes = entities.stream().map(mapper::toDomain).toList();

      return gerentes;

    }catch (Exception e){
      throw new RuntimeException("Erro ao pegar gerentes: " + e);
    }

  }
}
