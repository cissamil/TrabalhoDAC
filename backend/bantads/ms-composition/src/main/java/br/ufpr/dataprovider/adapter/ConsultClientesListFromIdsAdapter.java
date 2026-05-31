package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.mapper.ClienteResponseMapper;
import br.ufpr.dataprovider.mapper.PendingClienteResponseMapper;
import br.ufpr.dataprovider.client.domain.PendingClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultClientesListFromIdsAdapter implements ConsultClientesListFromIdsOutputPort {

  private final MsClienteClient msClienteClient;
  private final ClienteResponseMapper mapper;

  @Override
  public List<ClienteOutputData> consult(List<String> clienteIds) {

    List<ClienteResponse> responseList = msClienteClient.consultClientesListFromIds(clienteIds);

    List<ClienteOutputData> outputDataList = responseList.stream().map(mapper::toOutputData).toList();

    return outputDataList;
  }
}
