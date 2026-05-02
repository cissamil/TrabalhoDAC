package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.input.FindGerenteByGerenteIdInputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteByGerenteIdUseCase implements FindGerenteByGerenteIdInputPort {

  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;

  @Override
  public Gerente find(String gerenteId) {
    return findGerenteByGerenteIdOutputPort.find(gerenteId);
  }
}
