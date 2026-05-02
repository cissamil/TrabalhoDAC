package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.mapper.PendingClienteResponseMapper;
import br.ufpr.dataprovider.client.domain.PendingClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultClientesListFromIdsAdapter implements ConsultClientesListFromIdsOutputPort {

  private final MsClienteClient msClienteClient;
  private final PendingClienteResponseMapper mapper;

  @Override
  public List<PendingClienteOutputData> consult(List<String> clienteIds) {

    List<PendingClienteResponse> responseList = msClienteClient.consultClientesListFromIds(clienteIds);

    List<PendingClienteOutputData> outputDataList = responseList.stream().map(mapper::toOutputData).toList();

    return outputDataList;
  }
}
