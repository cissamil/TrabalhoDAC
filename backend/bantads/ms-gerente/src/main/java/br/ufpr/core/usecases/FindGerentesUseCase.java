package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.input.FindGerentesInputPort;
import br.ufpr.core.ports.output.FindGerentesOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerentesUseCase implements FindGerentesInputPort {

  private final FindGerentesOutputPort findGerentesOutputPort;

  @Override
  public List<Gerente> find() {

    return findGerentesOutputPort.find();
  }
}
