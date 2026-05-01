package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.model.response.PendingClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class PendingClienteResponseMapper {

  public PendingClienteOutputData toResponse(PendingClienteResponse clienteResponse){

    if(clienteResponse == null) return null;

    PendingClienteOutputData outputData = new PendingClienteOutputData();

    outputData.setNome(clienteResponse.getNome());
    outputData.setCpf(clienteResponse.getCpf());
    outputData.setEmail(clienteResponse.getEmail());
    outputData.setSalario(clienteResponse.getSalario());
    outputData.setClienteId(clienteResponse.getClienteId());

    return outputData;
  }


}
