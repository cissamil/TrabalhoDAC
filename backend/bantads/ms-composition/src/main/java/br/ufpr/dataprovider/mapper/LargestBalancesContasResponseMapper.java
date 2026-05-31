package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.LargestBalancesContasOutputData;
import br.ufpr.dataprovider.client.domain.LargestBalancesContasResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class LargestBalancesContasResponseMapper {
  public LargestBalancesContasOutputData toOutputData(LargestBalancesContasResponse response){

    if (response == null) return null;

    LargestBalancesContasOutputData outputData = new LargestBalancesContasOutputData();

    outputData.setSaldo(response.getSaldo());
    outputData.setContaId(response.getContaId());
    outputData.setClienteId(response.getClienteId());

    return outputData;

  }
}
