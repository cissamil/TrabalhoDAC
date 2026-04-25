package br.ufpr.core.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.ClienteResponseOutputData;
import br.ufpr.model.response.ClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteResponseMapper {

  public Cliente toDomain(ClienteResponseOutputData outputData){

    Cliente cliente = new Cliente();

    cliente.setId(outputData.getId());
    cliente.setClienteId(outputData.getClienteId());
    cliente.setCpf(outputData.getCpf());
    cliente.setNome(outputData.getNome());
    cliente.setEmail(outputData.getEmail());
    cliente.setTelefone(outputData.getTelefone());
    cliente.setSalario(outputData.getSalario());
    cliente.setEndereco(outputData.getEndereco());

    return cliente;

  }

  public ClienteResponse toResponse(Cliente cliente){

    ClienteResponse response = new ClienteResponse();

    response.setId(cliente.getId());
    response.setClienteId(cliente.getClienteId());
    response.setCpf(cliente.getCpf());
    response.setNome(cliente.getNome());
    response.setEmail(cliente.getEmail());
    response.setTelefone(cliente.getTelefone());
    response.setSalario(cliente.getSalario());
    response.setEndereco(cliente.getEndereco());

    return response;
  }
}
