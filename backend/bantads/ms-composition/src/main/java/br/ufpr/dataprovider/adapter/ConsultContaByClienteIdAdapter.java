package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.ports.output.ConsultContaByClienteIdOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.dataprovider.mapper.ContaResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultContaByClienteIdAdapter implements ConsultContaByClienteIdOutputPort {

  private final MsContaClient msContaClient;
  private final ContaResponseMapper mapper;

  @Override
  public ContaOutputData consult(String clienteId) {

    ContaResponse response = msContaClient.consultContaByClienteId(clienteId);

    return mapper.toOutputData(response);

  }
}
