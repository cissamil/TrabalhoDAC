package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import org.springframework.stereotype.Component;

@Component
public class GerenteResponseMapper {

  public GerenteOutputData toOutputData(GerenteResponse response){

    GerenteOutputData outputData = new GerenteOutputData();

    outputData.setGerenteId(response.getGerenteId());
    outputData.setNome(response.getNome());

    return outputData;
  }
}
