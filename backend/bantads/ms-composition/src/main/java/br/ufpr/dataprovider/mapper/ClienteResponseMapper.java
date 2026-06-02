package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.EnderecoOutputData;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.EnderecoResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteResponseMapper {

  public ClienteOutputData toOutputData(ClienteResponse response){

    if(response == null){
      return null;
    }

    ClienteOutputData outputData = new ClienteOutputData();

    EnderecoOutputData enderecoOutputData = getConvertedEnderecoToOutputData(response);

    outputData.setClienteId(response.getClienteId());
    outputData.setCpf(response.getCpf());
    outputData.setNome(response.getNome());
    outputData.setEmail(response.getEmail());
    outputData.setTelefone(response.getTelefone());
    outputData.setSalario(response.getSalario());
    outputData.setEndereco(enderecoOutputData);

    return outputData;
  }

  private EnderecoOutputData getConvertedEnderecoToOutputData(ClienteResponse response) {

    EnderecoResponse enderecoResponse = response.getEndereco();
    EnderecoOutputData enderecoOutputData = new EnderecoOutputData();

    enderecoOutputData.setId(enderecoResponse.getId());
    enderecoOutputData.setCep(enderecoResponse.getCep());
    enderecoOutputData.setNumero(enderecoResponse.getNumero());
    enderecoOutputData.setCidade(enderecoResponse.getCidade());
    enderecoOutputData.setEstado(enderecoResponse.getEstado());
    enderecoOutputData.setLogradouro(enderecoResponse.getLogradouro());

    return enderecoOutputData;
  }

}
