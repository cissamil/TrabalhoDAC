package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.GetClienteEmailFromClienteIdOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.model.response.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetClienteEmailFromClienteIdAdapter implements GetClienteEmailFromClienteIdOutputPort {

  private final MsClienteClient msClienteClient;

  @Override
  public String get(String clienteId) {

      ClienteResponse response = msClienteClient.getClienteByClienteId(clienteId);

      if(response == null) return null;
      return response.getEmail();
  }
}
