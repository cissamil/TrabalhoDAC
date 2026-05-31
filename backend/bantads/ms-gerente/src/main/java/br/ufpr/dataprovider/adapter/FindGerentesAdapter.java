package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.FindGerentesOutputPort;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.dataprovider.client.GerenteRepository;
import br.ufpr.dataprovider.mapper.GerenteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerentesAdapter implements FindGerentesOutputPort {

  private final GerenteEntityMapper mapper;
  private final GerenteRepository repository;

  @Override
  public List<Gerente> find() {

    List<GerenteEntity> entities = repository.findGerentes();

    List<Gerente> gerentes = entities.stream().map(mapper::toDomain).toList();

    return gerentes;
  }
}
