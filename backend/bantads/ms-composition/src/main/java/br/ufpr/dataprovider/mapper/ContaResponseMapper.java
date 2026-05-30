package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.dataprovider.client.domain.ContaResponse;
import org.springframework.stereotype.Component;

@Component
public class ContaResponseMapper {

  public ContaOutputData toOutputData (ContaResponse response){
    if (response == null) return null;

    ContaOutputData outputData = new ContaOutputData();

    outputData.setSaldo(response.getSaldo());
    outputData.setLimite(response.getLimite());
    outputData.setContaId(response.getContaId());
    outputData.setGerenteId(response.getGerenteId());
    outputData.setClienteId(response.getClienteId());
    outputData.setNumeroConta(response.getNumeroConta());

    return outputData;
  }
}
