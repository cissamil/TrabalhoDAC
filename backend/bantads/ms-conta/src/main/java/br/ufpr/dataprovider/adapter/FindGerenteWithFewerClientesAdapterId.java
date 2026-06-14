package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteWithFewerClientesIdOutputPort;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteWithFewerClientesAdapterId implements FindGerenteWithFewerClientesIdOutputPort {

  private final ContaQueryRepository repository;

  public String find(){

    return repository.findGerenteWithFewerClientesId();
  }

  @Override
  public String findWithoutSelectedGerente(String gerenteId) {

    return repository.findGerenteWithFewerClientesIdExceptSelectedGerente(gerenteId);
  }
}
