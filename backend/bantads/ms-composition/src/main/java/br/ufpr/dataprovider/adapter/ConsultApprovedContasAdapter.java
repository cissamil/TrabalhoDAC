package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.ports.output.ConsultApprovedContasOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.dataprovider.mapper.ContaResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// MS-COMPOSITION

@Component
@RequiredArgsConstructor
public class ConsultApprovedContasAdapter implements ConsultApprovedContasOutputPort {

  private final MsContaClient msContaClient;
  private final ContaResponseMapper mapper;

  @Override
  public List<ContaOutputData> consult() {

    List<ContaResponse> responseList = msContaClient.consultApprovedContas();

    System.out.println("Contas aprovadas encontrdas " + responseList.size());

    return responseList.stream()
      .map(mapper::toOutputData)
      .toList();
  }
}
