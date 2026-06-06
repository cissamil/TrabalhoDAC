package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.ports.output.ConsultClienteEmailOutputPort;
import br.ufpr.dataprovider.adapter.domain.ClienteResponse;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultClienteEmailAdapter implements ConsultClienteEmailOutputPort {

  private final MsClienteClient msClienteClient;

  @Override
  public ClienteOutputData consult(String clienteId) {

    ClienteResponse response = msClienteClient.consultClienteByClienteId(clienteId);

    if(response == null){
      throw new ResourceNotFoundException("Email do cliente não encontrado");
    }

    ClienteOutputData outputData = new ClienteOutputData();

    outputData.setClienteEmail(response.getEmail());

    return outputData;
  }
}
