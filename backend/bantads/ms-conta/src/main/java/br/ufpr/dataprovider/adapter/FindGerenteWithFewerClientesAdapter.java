package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteWithFewerClientesOutputPort;
import br.ufpr.dataprovider.client.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteWithFewerClientesAdapter implements FindGerenteWithFewerClientesOutputPort {

  public ContaRepository repository;

  public String find(){

    return repository.getGerenteWithFewerClientes();
  }
}
