package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.input.FindGerentesFromIdListInputPort;
import br.ufpr.core.ports.output.FindGerentesFromIdListOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerenteFromIdListUseCase implements FindGerentesFromIdListInputPort {

  private final FindGerentesFromIdListOutputPort findGerentesFromIdListOutputPort;

  @Override
  public List<Gerente> find(List<String> gerenteIds) {
    return findGerentesFromIdListOutputPort.find(gerenteIds);
  }
}
