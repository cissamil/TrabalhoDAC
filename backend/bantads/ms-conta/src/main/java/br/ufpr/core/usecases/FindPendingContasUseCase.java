package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindPendingContasInputPort;
import br.ufpr.core.ports.output.FindContasByGerenteAndStatusOutputPort;
import br.ufpr.model.enumerator.StatusConta;
import br.ufpr.model.response.PendingContaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-CONTA

@Component
@RequiredArgsConstructor
public class FindPendingContasUseCase implements FindPendingContasInputPort{

  private final FindContasByGerenteAndStatusOutputPort findContasByGerenteAndStatusOutputPort;

  @Override
  public List<Conta> find(String gerenteId) {

    return findContasByGerenteAndStatusOutputPort.find(gerenteId, StatusConta.CONTA_PENDENTE);

  }
}
