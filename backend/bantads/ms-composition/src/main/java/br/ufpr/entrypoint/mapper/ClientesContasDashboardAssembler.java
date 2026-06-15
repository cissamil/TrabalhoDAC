package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.ClientesContasDashboardOutputData;
import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.entrypoint.response.ClientesContasDashboardResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientesContasDashboardAssembler {

  public List<ClientesContasDashboardResponse> assemble(ClientesContasDashboardOutputData outputData){
    List<ContaOutputData> contas = outputData.getContas();
    List<ClienteOutputData> clientes = outputData.getClientes();

    return contas.stream().map(contaOutputData ->{

      if(contaOutputData == null) return null;

      ClienteOutputData cliente = getClienteFromList(contaOutputData, clientes);

      if (cliente == null) return null;

      return convertToClientesContasDashboardResponse(contaOutputData, cliente);
    }).toList();

  }

  private ClientesContasDashboardResponse convertToClientesContasDashboardResponse(ContaOutputData conta, ClienteOutputData cliente) {
    ClientesContasDashboardResponse response = new ClientesContasDashboardResponse();

    response.setNome(cliente.getNome());
    response.setSaldo(conta.getSaldo());
    response.setCpf(cliente.getCpf());
    response.setEndereco(cliente.getEndereco());
    response.setClienteId(cliente.getClienteId());
    response.setContaId(conta.getContaId());
    response.setLimite(conta.getLimite());
    response.setEmail(cliente.getEmail());
    response.setNumeroConta(conta.getNumeroConta());
    response.setTelefone(cliente.getTelefone());
    response.setSalario(cliente.getSalario());

    return response;
  }

  private ClienteOutputData getClienteFromList(ContaOutputData conta, List<ClienteOutputData> clientes) {
     return clientes.stream().filter(
        clienteOutputData -> clienteOutputData.getClienteId().equals(conta.getClienteId()))
      .findFirst()
      .orElse(null);
  }


}
