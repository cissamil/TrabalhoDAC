package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PendingClienteOutputData;
import br.ufpr.core.ports.output.ConsultBatchSearchClientesOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.mapper.PendingClienteResponseMapper;
import br.ufpr.model.response.PendingClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultBatchSearchClientesAdapter implements ConsultBatchSearchClientesOutputPort {

  private final MsClienteClient msClienteClient;
  private final PendingClienteResponseMapper mapper;

  @Override
  public List<PendingClienteOutputData> consult(List<String> clienteIds) {

    List<PendingClienteResponse> responseList = msClienteClient.consultBatchSearchClientes(clienteIds);

    List<PendingClienteOutputData> outputDataList = responseList.stream().map(mapper::toResponse).toList();

    return outputDataList;
  }
}
