package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.*;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.EnderecoResponse;
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
    ContaResponse contaResponse = buildContaResponseObject(conta);
    ClienteResponse clienteResponse = buildClienteResponseObject(cliente);

    dashboardResponse.setCliente(clienteResponse);
    dashboardResponse.setConta(contaResponse);
    dashboardResponse.setGerente(gerenteResponse);

    return dashboardResponse;
  }

  private GerenteResponse buildGerenteResponseObject(GerenteOutputData gerente) {
    GerenteResponse gerenteResponse = new GerenteResponse();

    gerenteResponse.setNomeGerente(gerente.getNome());
    return gerenteResponse;
  }

  private ContaResponse buildContaResponseObject(ContaOutputData conta) {
    ContaResponse contaResponse = new ContaResponse();

    contaResponse.setContaId(conta.getContaId());
    contaResponse.setNumeroConta(conta.getNumeroConta());
    contaResponse.setSaldo(conta.getSaldo());
    contaResponse.setLimite(conta.getLimite());
    return contaResponse;
  }

  private ClienteResponse buildClienteResponseObject(ClienteOutputData cliente){

    ClienteResponse clienteResponse = new ClienteResponse();
    EnderecoResponse endereco = buildEnderecoResponseObject(cliente);

    clienteResponse.setClienteId(cliente.getClienteId());
    clienteResponse.setCpf(cliente.getCpf());
    clienteResponse.setNome(cliente.getNome());
    clienteResponse.setEmail(cliente.getEmail());
    clienteResponse.setTelefone(cliente.getTelefone());
    clienteResponse.setSalario(cliente.getSalario());
    clienteResponse.setEndereco(endereco);

    return clienteResponse;
  }



  private EnderecoResponse buildEnderecoResponseObject(ClienteOutputData clienteOutputData) {

    EnderecoOutputData enderecoOutputData = clienteOutputData.getEndereco();
    EnderecoResponse enderecoResponse = new EnderecoResponse();

    enderecoResponse.setId(enderecoOutputData.getId());
    enderecoResponse.setCep(enderecoOutputData.getCep());
    enderecoResponse.setNumero(enderecoOutputData.getNumero());
    enderecoResponse.setCidade(enderecoOutputData.getCidade());
    enderecoResponse.setEstado(enderecoOutputData.getEstado());
    enderecoResponse.setLogradouro(enderecoOutputData.getLogradouro());

    return enderecoResponse;
  }

}
