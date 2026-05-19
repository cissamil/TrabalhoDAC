package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteWithFewerClientesIdOutputPort;
import br.ufpr.dataprovider.client.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteWithFewerClientesAdapterId implements FindGerenteWithFewerClientesIdOutputPort {

  private final ContaRepository repository;

  public String find(){

    return repository.findGerenteWithFewerClientesId();
  }
}
