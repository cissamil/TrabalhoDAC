package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteWithMostClientesIdOutputPort;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteWithMostClientesIdAdapter implements FindGerenteWithMostClientesIdOutputPort {

  private final ContaCommandRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public String find() {
    return repository.findGerenteWithMostClientesId();
  }
}
