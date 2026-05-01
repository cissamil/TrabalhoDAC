package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.model.response.PendingClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class PendingClienteResponseMapper {

  public PendingClienteResponse toResponse(Cliente cliente){

    if(cliente == null) return null;

    PendingClienteResponse response = new PendingClienteResponse();

    response.setNome(cliente.getNome());
    response.setCpf(cliente.getCpf());
    response.setEmail(cliente.getEmail());
    response.setSalario(cliente.getSalario());
    response.setClienteId(cliente.getClienteId());

    return response;
  }


}
