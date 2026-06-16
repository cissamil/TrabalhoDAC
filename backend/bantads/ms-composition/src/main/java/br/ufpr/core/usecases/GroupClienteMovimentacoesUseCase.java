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

    List<ClienteOutputData> clienteDestinoIds = extractClientesDestinoIdAndConsultOutputPort(transferencias);
    List<ClienteOutputData> clienteOrigemIds = extractClientesOrigemIdAndConsultOutputPort(transferencias);

    List<MovimentacaoOutputData> updatedMovimentacoesList = fillMovimentacoesWithClienteDestinoName(movimentacaoOutputDataList, clienteDestinoIds);

    updatedMovimentacoesList = fillMovimentacoesWithClienteOrigemName(movimentacaoOutputDataList, clienteOrigemIds);

    System.out.println("Movimentações encontradas: " + updatedMovimentacoesList);

    dashboardOutputData.setMovimentacoes(updatedMovimentacoesList);

    return dashboardOutputData;
  }

  private static List<MovimentacaoOutputData> getTransferencias(List<MovimentacaoOutputData> movimentacaoOutputDataList) {
    return movimentacaoOutputDataList.stream()
      .filter(movimentacao -> movimentacao.getTipoMovimentacao() == TipoMovimentacao.TRANSFERENCIA)
      .toList();
  }

  private List<MovimentacaoOutputData> fillMovimentacoesWithClienteDestinoName(List<MovimentacaoOutputData> movimentacaoOutputDataList, List<ClienteOutputData> clienteDestinoIds) {
    return movimentacaoOutputDataList.stream().map(movimentacao -> {



      if (!movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.TRANSFERENCIA)) return movimentacao;

      ClienteOutputData cliente = getClienteDestinoIdFromList(movimentacao, clienteDestinoIds);

      if (cliente == null) return movimentacao;

      movimentacao.setClienteDestinoNome(cliente.getNome());

      return movimentacao;
    }).toList();
  }


  private List<MovimentacaoOutputData> fillMovimentacoesWithClienteOrigemName(List<MovimentacaoOutputData> movimentacaoOutputDataList, List<ClienteOutputData> clienteOrigemIds) {
    return movimentacaoOutputDataList.stream().map(movimentacao -> {

      if (!movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.TRANSFERENCIA)) return movimentacao;

      ClienteOutputData cliente = getClienteOrigemIdFromList(movimentacao, clienteOrigemIds);

      if (cliente == null) return movimentacao;

      movimentacao.setClienteOrigemNome(cliente.getNome());

      return movimentacao;
    }).toList();
  }

  private ClienteOutputData getClienteDestinoIdFromList(MovimentacaoOutputData movimentacao, List<ClienteOutputData> clientes) {
    return clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(movimentacao.getClienteDestinoId()))
      .findFirst()
      .orElse(null);
  }

  private ClienteOutputData getClienteOrigemIdFromList(MovimentacaoOutputData movimentacao, List<ClienteOutputData> clientes) {
    return clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(movimentacao.getClienteOrigemId()))
      .findFirst()
      .orElse(null);
  }
  private List<ClienteOutputData> extractClientesDestinoIdAndConsultOutputPort(List<MovimentacaoOutputData> transferencias) {

    List<String> clienteDestinoIdList = transferencias.stream().
      map(MovimentacaoOutputData::getClienteDestinoId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteDestinoIdList);

  }


  private List<ClienteOutputData> extractClientesOrigemIdAndConsultOutputPort(List<MovimentacaoOutputData> transferencias) {

    List<String> clienteDestinoIdList = transferencias.stream().
      map(MovimentacaoOutputData::getClienteOrigemId)
      .distinct()
      .toList();

    System.out.println("Buscando clientes pendentes \n");

    return consultClientesListFromIdsOutputPort.consult(clienteDestinoIdList);

  }
}
