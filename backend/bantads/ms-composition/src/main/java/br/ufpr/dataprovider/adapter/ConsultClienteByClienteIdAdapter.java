package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.ports.output.ConsultClienteByClienteIdOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.mapper.ClienteResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultClienteByClienteIdAdapter implements ConsultClienteByClienteIdOutputPort {

  private final MsClienteClient msClienteClient;
  private final ClienteResponseMapper mapper;

  @Override
  public ClienteOutputData consult(String clienteId) {

    ClienteResponse clienteResponse = msClienteClient.consultClienteByClienteId(clienteId);

    return mapper.toOutputData(clienteResponse);
  }
}
