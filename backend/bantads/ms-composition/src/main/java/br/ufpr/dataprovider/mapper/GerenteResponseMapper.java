package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import org.springframework.stereotype.Component;

@Component
public class GerenteResponseMapper {

  public GerenteOutputData toOutputData(GerenteResponse response){

    if(response == null) return null;

    GerenteOutputData outputData = new GerenteOutputData();

    outputData.setNome(response.getNome());
    outputData.setCpf(response.getCpf());
    outputData.setGerenteId(response.getGerenteId());

    return outputData;
  }
}
