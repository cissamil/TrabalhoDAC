package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupLargestBalanceContasWIthClienteInputPort;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultLargestBalanceContasOutputPort;
import infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupLargestBalanceContasWIthClienteUseCase implements GroupLargestBalanceContasWIthClienteInputPort {

  private final ConsultLargestBalanceContasOutputPort consultLargestBalanceContasOutputPort;
  private final ConsultClientesListFromIdsOutputPort consultClientesListFromIdsOutputPort;

  @Override
  public LargestBalanceContasDashboardOutputData execute() {

    LargestBalanceContasDashboardOutputData largestBalanceContasDashboardOutputData = new LargestBalanceContasDashboardOutputData();

    int quantity = 3;

    List<LargestBalancesContasOutputData> largestBalancesContas = consultLargestBalanceContasOutputPort.consult(quantity);

    validateContasList(largestBalancesContas);

    List<ClienteOutputData> clienteOutputDataList = extractClientesIdAndConsultOutputPort(largestBalancesContas);

    largestBalanceContasDashboardOutputData.setClientes(clienteOutputDataList);
    largestBalanceContasDashboardOutputData.setContas(largestBalancesContas);

    return largestBalanceContasDashboardOutputData;
  }

  private void validateContasList(List<LargestBalancesContasOutputData> largestBalancesContas) {
    if(largestBalancesContas == null || largestBalancesContas.isEmpty()){
      throw new ResourceNotFoundException("Contas não encontradas");
    }
  }

  private List<ClienteOutputData> extractClientesIdAndConsultOutputPort(List<LargestBalancesContasOutputData> largestBalancesContasOutputData) {
    if(largestBalancesContasOutputData.isEmpty()) return new ArrayList<>();

    List<String> clienteIdList = largestBalancesContasOutputData.stream().
      map(LargestBalancesContasOutputData::getClienteId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteIdList);

  }
}
