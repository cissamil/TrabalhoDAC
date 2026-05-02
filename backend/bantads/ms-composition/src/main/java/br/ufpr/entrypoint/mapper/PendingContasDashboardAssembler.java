package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.core.domain.PendingContasDashboardOutputData;
import br.ufpr.entrypoint.response.ContaClienteDetailResponse;
import br.ufpr.entrypoint.response.GerenteSummaryResponse;
import br.ufpr.entrypoint.response.PendingContasDashboardResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PendingContasDashboardAssembler {

  public PendingContasDashboardResponse assemble(PendingContasDashboardOutputData outputData){
    GerenteSummaryResponse gerenteSummaryResponse = extractGerenteSummary(outputData.getGerenteOutputData());
    List<ContaClienteDetailResponse> contaClienteDetailResponseList = groupContasAndClientes(
      outputData.getContaOutputDataList(),
      outputData.getClienteOutputDataList()
    );

    PendingContasDashboardResponse dashboardResponse = new PendingContasDashboardResponse();

    dashboardResponse.setGerente(gerenteSummaryResponse);
    dashboardResponse.setContas(contaClienteDetailResponseList);

    return dashboardResponse;
  }

  private static GerenteSummaryResponse extractGerenteSummary(GerenteOutputData gerente) {
    GerenteSummaryResponse gerenteSummaryResponse = new GerenteSummaryResponse();

    gerenteSummaryResponse.setNome(gerente.getNome());
    gerenteSummaryResponse.setGerenteId(gerente.getGerenteId());
    return gerenteSummaryResponse;
  }

  private List<ContaClienteDetailResponse> groupContasAndClientes(List<PendingContaOutputData> contas, List<PendingClienteOutputData> clientes) {

    List<ContaClienteDetailResponse> contaClienteDetailResponseList;

    Map<String, PendingClienteOutputData> clientesMap = clientes.stream()
      .collect(Collectors.toMap(PendingClienteOutputData::getClienteId, cliente -> cliente));

    contaClienteDetailResponseList = contas.stream().map(conta ->{

      PendingClienteOutputData clienteOfConta = clientesMap.get(conta.getClienteId());

      ContaClienteDetailResponse detailResponse = new ContaClienteDetailResponse();

      detailResponse.setContaId(conta.getId());
      detailResponse.setClienteId(conta.getClienteId());

      if(clienteOfConta != null){

        detailResponse.setClienteCpf(clienteOfConta.getCpf());
        detailResponse.setClienteNome(clienteOfConta.getNome());
        detailResponse.setClienteEmail(clienteOfConta.getEmail());
        detailResponse.setClienteSalario(clienteOfConta.getSalario());
      }

      return detailResponse;
    }).toList();

    return contaClienteDetailResponseList;
  }
}
