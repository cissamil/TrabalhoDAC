package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupClienteMovimentacoesInputPort;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultContaMovimentacoesOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupClienteMovimentacoesUseCase implements GroupClienteMovimentacoesInputPort {

  private final ConsultContaMovimentacoesOutputPort consultContaMovimentacoesOutputPort;
  private final ConsultClientesListFromIdsOutputPort consultClientesListFromIdsOutputPort;

  @Override
  public ClienteMovimentacoesDashboardOutputData execute(ConsultBankStatementInputData inputData){

    ClienteMovimentacoesDashboardOutputData dashboardOutputData = new ClienteMovimentacoesDashboardOutputData();

    dashboardOutputData.setClienteId(inputData.getClienteId());

    List<MovimentacaoOutputData> movimentacaoOutputDataList = consultContaMovimentacoesOutputPort.consult(inputData);

    List<MovimentacaoOutputData> transferencias = getTransferencias(movimentacaoOutputDataList);

    if(transferencias.isEmpty()){
      dashboardOutputData.setMovimentacoes(movimentacaoOutputDataList);
      return dashboardOutputData;
    }

    List<ClienteOutputData> clienteDestinoIds = extractClientesIdAndConsultOutputPort(transferencias);

    List<MovimentacaoOutputData> updatedMovimentacoesList = fillMovimentacoeWithClienteDestinoName(movimentacaoOutputDataList, clienteDestinoIds);

    dashboardOutputData.setMovimentacoes(updatedMovimentacoesList);

    return dashboardOutputData;
  }

  private static List<MovimentacaoOutputData> getTransferencias(List<MovimentacaoOutputData> movimentacaoOutputDataList) {
    return movimentacaoOutputDataList.stream()
      .filter(movimentacao -> movimentacao.getTipoMovimentacao() == TipoMovimentacao.TRANSFERENCIA)
      .toList();
  }

  private List<MovimentacaoOutputData> fillMovimentacoeWithClienteDestinoName(List<MovimentacaoOutputData> movimentacaoOutputDataList, List<ClienteOutputData> clienteDestinoIds) {
    return movimentacaoOutputDataList.stream().map(movimentacao -> {

      if (!movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.TRANSFERENCIA)) return movimentacao;

      ClienteOutputData cliente = getClienteFromList(movimentacao, clienteDestinoIds);

      if (cliente == null) return movimentacao;

      movimentacao.setClienteDestinoNome(cliente.getNome());

      return movimentacao;
    }).toList();
  }

  private ClienteOutputData getClienteFromList(MovimentacaoOutputData movimentacao, List<ClienteOutputData> clientes) {
    return clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(movimentacao.getClienteDestinoId()))
      .findFirst()
      .orElse(null);
  }
  private List<ClienteOutputData> extractClientesIdAndConsultOutputPort(List<MovimentacaoOutputData> transferencias) {

    List<String> clienteDestinoIdList = transferencias.stream().
      map(MovimentacaoOutputData::getClienteDestinoId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteDestinoIdList);

  }
}
