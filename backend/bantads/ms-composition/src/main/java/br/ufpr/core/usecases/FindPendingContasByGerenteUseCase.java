package br.ufpr.core.usecases;

import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.core.ports.input.FindPendingContasByGerenteInputPort;
import br.ufpr.core.ports.output.ConsultPendingContasOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-COMPOSITION

@Component
@RequiredArgsConstructor
public class FindPendingContasByGerenteUseCase implements FindPendingContasByGerenteInputPort {

  private final ConsultPendingContasOutputPort consultPendingContasOutputPort;

  @Override
  public void find(String gerenteId) {

    System.out.println("Buscando contas pendentes \n");

    List<PendingContaOutputData> outputDataList = consultPendingContasOutputPort.consult(gerenteId);

    System.out.println(outputDataList.toArray().length + " contas encontradas");

    outputDataList.forEach(item -> {
        System.out.println("\n [CONTA] Id: " + item.getId() + " - Cliente Id" + item.getClienteId());
      }
    );
  }
}
