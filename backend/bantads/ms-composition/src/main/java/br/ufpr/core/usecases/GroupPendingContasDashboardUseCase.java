package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupPendingContasDashboardInputPort;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultGerenteOutputPort;
import br.ufpr.core.ports.output.ConsultPendingContasOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// MS-COMPOSITION

@Component
@RequiredArgsConstructor
public class GroupPendingContasDashboardUseCase implements GroupPendingContasDashboardInputPort {

  private final ConsultGerenteOutputPort consultGerenteOutputPort;
  private final ConsultPendingContasOutputPort consultPendingContasOutputPort;
  private final ConsultClientesListFromIdsOutputPort consultClientesListFromIdsOutputPort;

  @Override
  public PendingContasDashboardOutputData execute(String gerenteId) {

    System.out.println("Buscando contas pendentes e dados do gerente \n");

    GerentesContasAsyncConsult asyncConsult = gerenteAndContaOutputPortsMultipleAsyncConsult(gerenteId);

    GerenteOutputData gerente = asyncConsult.getGerenteOutputData();
    List<PendingContaOutputData> contas = asyncConsult.getContaOutputDataList();

    List<PendingClienteOutputData> clientes = extractClientesIdAndConsultOutputPort(contas);

    return new PendingContasDashboardOutputData(gerente, contas, clientes);

  }

  private GerentesContasAsyncConsult gerenteAndContaOutputPortsMultipleAsyncConsult(String gerenteId) {
    CompletableFuture<List<PendingContaOutputData>> contaFuture = CompletableFuture.supplyAsync(() ->
      consultPendingContasOutputPort.consult(gerenteId)
    );

    CompletableFuture<GerenteOutputData> gerenteFuture = CompletableFuture.supplyAsync(() ->
      consultGerenteOutputPort.consult(gerenteId)
    );

    CompletableFuture.allOf(contaFuture, gerenteFuture).join();

    GerentesContasAsyncConsult asyncConsult = new GerentesContasAsyncConsult();

    asyncConsult.setGerenteOutputData(gerenteFuture.join());
    asyncConsult.setContaOutputDataList(contaFuture.join());

    return asyncConsult;
  }

  private List<PendingClienteOutputData> extractClientesIdAndConsultOutputPort(List<PendingContaOutputData> pendingContaOutputDataList) {
    if(pendingContaOutputDataList.isEmpty()) return new ArrayList<>();

    List<String> clienteIdList = pendingContaOutputDataList.stream().
      map(PendingContaOutputData::getClienteId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteIdList);

  }

}
