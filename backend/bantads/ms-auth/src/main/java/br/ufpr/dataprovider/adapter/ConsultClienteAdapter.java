package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.ports.output.ConsultClienteOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultClienteAdapter implements ConsultClienteOutputPort {

  private final MsClienteClient msClienteClient;

  @Override
  public ClienteOutputData consult(String clienteId) {

    ClienteResponse response = msClienteClient.consultClienteByClienteId(clienteId);

    System.out.println("[MS-AUTH] Cliente: " + response.getEmail());

    return new ClienteOutputData(response.getEmail());

  }
}
