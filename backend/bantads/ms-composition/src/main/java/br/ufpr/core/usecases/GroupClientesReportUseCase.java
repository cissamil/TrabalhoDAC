package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupClientesReportInputPort;
import br.ufpr.core.ports.output.ConsultApprovedContasOutputPort;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultGerentesListFromIdsOutputPort;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class GroupClientesReportUseCase implements GroupClientesReportInputPort {

  private final ConsultApprovedContasOutputPort consultApprovedContasOutputPort;
  private final ConsultClientesListFromIdsOutputPort consultClientesListFromIdsOutputPort;
  private final ConsultGerentesListFromIdsOutputPort consultGerentesListFromIdsOutputPort;

  @Override
  public ClientesReportDashboardOutputData execute() {

    ClientesReportDashboardOutputData clientesReportDashboardOutputData = new ClientesReportDashboardOutputData();

    List<ContaOutputData> contaOutputDataList = consultApprovedContasOutputPort.consult(" ");

    validateContasList(contaOutputDataList);
    List<String> clientesIds = extractClientesIdAndConsultOutputPort(contaOutputDataList);
    List<String> gerentesIds = extractGerentesIdAndConsultOutputPort(contaOutputDataList);

    System.out.println("Ids de clientes: " + clientesIds);
    ClientesGerentesAsyncConsult asyncConsult = clienteAndGerenteOutputPortsMultipleAsyncConsult(clientesIds, gerentesIds);

    List<ClienteOutputData> clienteOutputDataList = asyncConsult.getClientes();
    List<GerenteOutputData> gerenteOutputDataList = asyncConsult.getGerentes();

    System.out.println("Clientes: " + clienteOutputDataList + " Gerentes: " + gerenteOutputDataList + " Contas: " + contaOutputDataList);

    clientesReportDashboardOutputData.setContas(contaOutputDataList);
    clientesReportDashboardOutputData.setClientes(clienteOutputDataList);
    clientesReportDashboardOutputData.setGerentes(gerenteOutputDataList);

    return clientesReportDashboardOutputData;
  }

  private void validateContasList(List<ContaOutputData> contaOutputDataList) {
    if(contaOutputDataList == null){
      throw new ResourceNotFoundException("Contas não encontradas");
    }
  }
  private ClientesGerentesAsyncConsult clienteAndGerenteOutputPortsMultipleAsyncConsult(List<String> clienteIds, List<String> gerenteIds) {
    CompletableFuture<List<ClienteOutputData>> clienteFuture = CompletableFuture.supplyAsync(() ->
      consultClientesListFromIdsOutputPort.consult(clienteIds)
    );

    CompletableFuture<List<GerenteOutputData>> gerenteFuture = CompletableFuture.supplyAsync(() ->
      consultGerentesListFromIdsOutputPort.consult(gerenteIds)
    );

    CompletableFuture.allOf(clienteFuture, gerenteFuture).join();

    ClientesGerentesAsyncConsult asyncConsult = new ClientesGerentesAsyncConsult();

    asyncConsult.setGerentes(gerenteFuture.join());
    asyncConsult.setClientes(clienteFuture.join());

    return asyncConsult;
  }

  private List<String> extractClientesIdAndConsultOutputPort(List<ContaOutputData> contaOutputDataList) {
    if(contaOutputDataList.isEmpty()) return new ArrayList<>();

    return contaOutputDataList.stream().
      map(ContaOutputData::getClienteId)
      .distinct()
      .toList();
  }

  private List<String> extractGerentesIdAndConsultOutputPort(List<ContaOutputData> contaOutputDataList) {
    if(contaOutputDataList.isEmpty()) return new ArrayList<>();

    return contaOutputDataList.stream().
      map(ContaOutputData::getGerenteId)
      .distinct()
      .toList();


  }
}
