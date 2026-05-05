package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.ClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MsCliente", url = "${endpoints.cliente}")
public interface MsClienteClient {

  @GetMapping(value = "/{clienteId}")
  ClienteResponse consultClienteByClienteId(@PathVariable("clienteId") String clienteId);
}
