package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.LargestBalancesContasOutputData;
import br.ufpr.core.ports.output.ConsultLargestBalanceContasOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.client.domain.LargestBalancesContasResponse;
import br.ufpr.dataprovider.mapper.LargestBalancesContasResponseMapper;
import feign.FeignException;
import infrastructure.exceptions.UnavailableServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultLargestBalanceContasAdapter implements ConsultLargestBalanceContasOutputPort{

  private final MsContaClient msContaClient;
  private final LargestBalancesContasResponseMapper mapper;

  @Override
  public List<LargestBalancesContasOutputData> consult(int quantity) {
    try{

      List<LargestBalancesContasResponse> responseList = msContaClient.consultContasByQuantity(quantity);

      List<LargestBalancesContasOutputData> outputDataList = responseList.stream().map(mapper::toOutputData).toList();

      return outputDataList;

    } catch (FeignException e){

      throw new UnavailableServiceException("Serviço de contas indisponível");

    }
  }
}
