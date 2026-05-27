package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.FindClientesInputPort;

import java.util.List;

public class FindClientesUseCase implements FindClientesInputPort {
  @Override
  public List<Cliente> find() {
    return List.of();
  }
}
