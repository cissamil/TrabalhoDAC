package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupClientesConsultInputPort;
import br.ufpr.core.ports.input.GroupClientesReportInputPort;
import br.ufpr.core.ports.output.ConsultApprovedContasOutputPort;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultGerentesListFromIdsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class GroupClientesConsultUseCase implements GroupClientesConsultInputPort {

  private final ConsultApprovedContasOutputPort consultApprovedContasOutputPort;
  private final ConsultClientesListFromIdsOutputPort consultClientesListFromIdsOutputPort;

  @Override
  public ClientesContasDashboardOutputData execute(String gerenteId) {

    ClientesContasDashboardOutputData outputData = new ClientesContasDashboardOutputData();

    List<ContaOutputData> contaOutputDataList = consultApprovedContasOutputPort.consult(gerenteId);

    validateContasList(contaOutputDataList);

    List<ClienteOutputData> clienteOutputDataList = extractClientesIdAndConsultOutputPort(contaOutputDataList);

    outputData.setContas(contaOutputDataList);
    outputData.setClientes(clienteOutputDataList);

    return outputData;
  }

  private void validateContasList(List<ContaOutputData> contaOutputDataList) {
    if(contaOutputDataList == null || contaOutputDataList.isEmpty()){
      throw new RuntimeException("Erro ao pegar as contas");
    }
  }

  private List<ClienteOutputData> extractClientesIdAndConsultOutputPort(List<ContaOutputData> contaOutputDataList) {
    if(contaOutputDataList.isEmpty()) return new ArrayList<>();

    List<String> clienteIdList = contaOutputDataList.stream().
      map(ContaOutputData::getClienteId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteIdList);

  }
}
