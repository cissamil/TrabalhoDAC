package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.core.ports.output.ConsultPendingContasOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.mapper.PendingContaResponseMapper;
import br.ufpr.model.response.PendingContaResponse;
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

    List<PendingContaResponse> responseList = msContaClient.consultPendingContas(gerenteId);

    List<PendingContaOutputData> contaOutputDataList = responseList.stream()
      .map(mapper::toOutputData)
      .toList();

    return contaOutputDataList;
  }
}
