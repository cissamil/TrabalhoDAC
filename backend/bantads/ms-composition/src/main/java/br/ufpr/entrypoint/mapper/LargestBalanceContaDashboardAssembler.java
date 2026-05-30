package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.LargestBalanceContasDashboardOutputData;
import br.ufpr.core.domain.LargestBalancesContasOutputData;
import br.ufpr.entrypoint.response.LargestBalanceContaDashboardResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LargestBalanceContaDashboardAssembler {

  public List<LargestBalanceContaDashboardResponse> assemble(LargestBalanceContasDashboardOutputData outputData){


    List<ClienteOutputData> clientes = outputData.getClientes();
    List<LargestBalancesContasOutputData> contas = outputData.getContas();

    List<LargestBalanceContaDashboardResponse> responseList = contas.stream().map(conta -> {

      if(conta == null) return null;

      LargestBalanceContaDashboardResponse response = new LargestBalanceContaDashboardResponse();

      ClienteOutputData cliente = clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(conta.getClienteId()))
        .findFirst()
        .orElse(null);

        if(cliente == null) return null;

        response.setNome(cliente.getNome());
        response.setSaldo(conta.getSaldo());
        response.setCpf(cliente.getCpf());
        response.setEndereco(cliente.getEndereco());
        response.setClienteId(cliente.getClienteId());
        response.setContaId(conta.getContaId());

        return response;
    }).toList();


    return responseList;
  }
}
