package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteWithMostClientesIdOutputPort;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteWithMostClientesIdAdapter implements FindGerenteWithMostClientesIdOutputPort {

  private final ContaQueryRepository repository;

  @Override
  public String find() {
    return repository.findGerenteWithMostClientesId();
  }
}
