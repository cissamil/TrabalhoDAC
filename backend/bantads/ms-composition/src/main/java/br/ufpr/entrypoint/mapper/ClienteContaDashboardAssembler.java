package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ClienteContaDashboardOutputData;
import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.entrypoint.response.ClienteContaDashboardResponse;
import br.ufpr.entrypoint.response.ContaResponse;
import br.ufpr.entrypoint.response.GerenteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteContaDashboardAssembler {

  public ClienteContaDashboardResponse assemble(ClienteContaDashboardOutputData clienteContaDashboardOutputData){

    if (clienteContaDashboardOutputData == null) return null;

    ClienteContaDashboardResponse dashboardResponse = new ClienteContaDashboardResponse();

    ClienteOutputData cliente = clienteContaDashboardOutputData.getClienteOutputData();
    ContaOutputData conta = clienteContaDashboardOutputData.getContaOutputData();
    GerenteOutputData gerente = clienteContaDashboardOutputData.getGerenteOutputData();

    GerenteResponse gerenteResponse = buildGerenteResponseObject(gerente);
    ContaResponse contaResponse = buildContaResponseObject(conta, gerenteResponse);


    dashboardResponse.setClienteId(cliente.getClienteId());
    dashboardResponse.setCpf(cliente.getCpf());
    dashboardResponse.setNome(cliente.getNome());
    dashboardResponse.setEmail(cliente.getEmail());
    dashboardResponse.setTelefone(cliente.getTelefone());
    dashboardResponse.setSalario(cliente.getSalario());
    dashboardResponse.setEndereco(cliente.getEndereco());
    dashboardResponse.setConta(contaResponse);

    return dashboardResponse;
  }

  private static GerenteResponse buildGerenteResponseObject(GerenteOutputData gerente) {
    GerenteResponse gerenteResponse = new GerenteResponse();

    gerenteResponse.setNomeGerente(gerente.getNome());
    return gerenteResponse;
  }

  private static ContaResponse buildContaResponseObject(ContaOutputData conta, GerenteResponse gerenteResponse) {
    ContaResponse contaResponse = new ContaResponse();

    contaResponse.setNumeroConta(conta.getNumeroConta());
    contaResponse.setId(conta.getId());
    contaResponse.setSaldo(conta.getSaldo());
    contaResponse.setLimite(conta.getLimite());
    contaResponse.setGerente(gerenteResponse);
    return contaResponse;
  }
}
