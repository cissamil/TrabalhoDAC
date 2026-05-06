package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteResponseMapper {

  public ClienteOutputData toOutputData(ClienteResponse response){

    if(response == null){
      return null;
    }

    ClienteOutputData outputData = new ClienteOutputData();

    outputData.setClienteId(response.getClienteId());
    outputData.setCpf(response.getCpf());
    outputData.setNome(response.getNome());
    outputData.setEmail(response.getEmail());
    outputData.setTelefone(response.getTelefone());
    outputData.setSalario(response.getSalario());
    outputData.setEndereco(response.getEndereco());

    return outputData;

  }

}
