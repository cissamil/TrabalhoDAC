package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.dataprovider.client.domain.PendingContaResponse;
import org.springframework.stereotype.Component;

@Component
public class PendingContaResponseMapper {

  public PendingContaOutputData toOutputData(PendingContaResponse response){

    if(response == null) return null;

    PendingContaOutputData outputData = new PendingContaOutputData();


    outputData.setId(response.getId());
    outputData.setClienteId(response.getClienteId());
    outputData.setGerenteId(response.getGerenteId());

    return outputData;
  }


}
