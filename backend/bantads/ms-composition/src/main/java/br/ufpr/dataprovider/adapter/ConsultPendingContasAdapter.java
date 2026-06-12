package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.core.ports.output.ConsultPendingContasOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.mapper.PendingContaResponseMapper;
import br.ufpr.dataprovider.client.domain.PendingContaResponse;
import feign.FeignException;
import br.ufpr.infrastructure.exceptions.UnavailableServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-COMPOSITION

@Component
@RequiredArgsConstructor
public class ConsultPendingContasAdapter implements ConsultPendingContasOutputPort {

  private final MsContaClient msContaClient;
  private final PendingContaResponseMapper mapper;

  @Override
  public List<PendingContaOutputData> consult(String gerenteId) {

    try{

      List<PendingContaResponse> responseList = msContaClient.consultPendingContas(gerenteId);

      List<PendingContaOutputData> contaOutputDataList = responseList.stream()
        .map(mapper::toOutputData)
        .toList();

      return contaOutputDataList;

    }catch (FeignException e){

      throw new UnavailableServiceException("Serviço de contas indisponível");

    }

  }
}
