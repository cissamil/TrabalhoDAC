package br.ufpr.core.usecases;

import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.core.ports.input.FindPendingContasByGerenteInputPort;
import br.ufpr.core.ports.output.ConsultBatchSearchClientesOutputPort;
import br.ufpr.core.ports.output.ConsultPendingContasOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-COMPOSITION

@Component
@RequiredArgsConstructor
public class FindPendingContasByGerenteUseCase implements FindPendingContasByGerenteInputPort {

  private final ConsultPendingContasOutputPort consultPendingContasOutputPort;
  private final ConsultBatchSearchClientesOutputPort consultBatchSearchClientesOutputPort;

  @Override
  public void find(String gerenteId) {

    System.out.println("Buscando contas pendentes \n");

    List<PendingContaOutputData> pendingContaOutputDataList = consultPendingContasOutputPort.consult(gerenteId);

    System.out.println(pendingContaOutputDataList.toArray().length + " contas encontradas");

    List<String> clienteIdList = extractClienteIdsFromContaList(pendingContaOutputDataList);

    System.out.println("Buscando clientes pendentes \n");

    List<PendingClienteOutputData> pendingClienteOutputDataList = consultBatchSearchClientesOutputPort.consult(clienteIdList);

    System.out.println(pendingClienteOutputDataList.toArray().length + " clientes encontrados");

    pendingContaOutputDataList.forEach(item -> {
      System.out.println("\n [CONTA] Id: " + item.getId() + " - Cliente Id" + item.getClienteId());
    });

    pendingClienteOutputDataList.forEach(item -> System.out.println("[CLIENTE] nome: " + item.getNome() + " - email: " + item.getEmail()));
  }

  private List<String> extractClienteIdsFromContaList(List<PendingContaOutputData> pendingContaOutputDataList) {
    List<String> clienteIdList = pendingContaOutputDataList.stream().
      map(PendingContaOutputData::getClienteId)
      .distinct()
      .toList();

    return clienteIdList;
  }
}
