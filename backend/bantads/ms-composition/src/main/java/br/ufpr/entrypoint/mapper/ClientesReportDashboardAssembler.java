package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.ClientesReportDashboardOutputData;
import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.entrypoint.response.ClienteReportResponse;
import br.ufpr.entrypoint.response.ClientesReportDashboardResponse;
import br.ufpr.entrypoint.response.ContaReportResponse;
import br.ufpr.entrypoint.response.GerenteReportResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientesReportDashboardAssembler {

  public List<ClientesReportDashboardResponse> assemble(ClientesReportDashboardOutputData outputData){

    List<ContaOutputData> contas = outputData.getContas();
    List<ClienteOutputData> clientes = outputData.getClientes();
    List<GerenteOutputData> gerentes = outputData.getGerentes();

    return contas.stream().map( conta ->{
      ClientesReportDashboardResponse response = new ClientesReportDashboardResponse();

      ContaReportResponse contaReportResponse = toContaReportResponse(conta);
      ClienteReportResponse clienteReportResponse = getClienteFromList(conta, clientes);
      GerenteReportResponse gerenteReportResponse = getGerenteFromList(conta, gerentes);

      response.setConta(contaReportResponse);
      response.setCliente(clienteReportResponse);
      response.setGerente(gerenteReportResponse);

      return response;

    }).toList();

  }


  private ClienteReportResponse getClienteFromList(ContaOutputData conta, List<ClienteOutputData> clientes){
    ClienteOutputData outputData = clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(conta.getClienteId()))
      .findFirst()
      .orElse(null);

    return toClienteReportResponse(outputData);
  }

  private GerenteReportResponse getGerenteFromList(ContaOutputData conta, List<GerenteOutputData> gerentes){
    GerenteOutputData outpuData = gerentes.stream().filter(
        gerenteOutputData -> gerenteOutputData.getGerenteId().equals(conta.getGerenteId()))
      .findFirst()
      .orElse(null);

    return toGerenteReportResponse(outpuData);
  }

  private ContaReportResponse toContaReportResponse(ContaOutputData outputData){

    if(outputData == null) return null;

    ContaReportResponse response = new ContaReportResponse();

    response.setContaId(outputData.getContaId());
    response.setSaldo(outputData.getSaldo());
    response.setLimite(outputData.getLimite());
    response.setNumeroConta(outputData.getNumeroConta());

    return  response;
  }

  private ClienteReportResponse toClienteReportResponse(ClienteOutputData outputData){

    if(outputData == null) return null;

    ClienteReportResponse response = new ClienteReportResponse();

    response.setClienteId(outputData.getClienteId());
    response.setNome(outputData.getNome());
    response.setCpf(outputData.getCpf());
    response.setSalario(outputData.getSalario());
    response.setEmail(outputData.getEmail());

    return  response;
  }
  private GerenteReportResponse toGerenteReportResponse(GerenteOutputData outputData){

    if(outputData == null) return null;

    GerenteReportResponse response = new GerenteReportResponse();

    response.setCpf(outputData.getCpf());
    response.setNome(outputData.getNome());
    response.setGerenteId(outputData.getGerenteId());


    return  response;
  }

}
