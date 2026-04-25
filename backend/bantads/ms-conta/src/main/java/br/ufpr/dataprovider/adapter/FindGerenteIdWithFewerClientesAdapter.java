package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteIdWithFewerClientesOutputPort;
import br.ufpr.dataprovider.client.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteIdWithFewerClientesAdapter implements FindGerenteIdWithFewerClientesOutputPort {

  private final ContaRepository repository;

  public String find(){

    return repository.findGerenteIdWithFewerClientes();
  }
}
