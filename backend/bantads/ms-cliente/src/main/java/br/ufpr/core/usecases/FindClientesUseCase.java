package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.FindClientesInputPort;
import br.ufpr.core.ports.output.FindClientesOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindClientesUseCase implements FindClientesInputPort {

  private final FindClientesOutputPort findClientesOutputPort;

  @Override
  public List<Cliente> find() {

    return findClientesOutputPort.find();
  }
}
