package br.ufpr.dataprovider.client;

import br.ufpr.model.response.ClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MsCliente", url = "http://localhost:8082")
public interface MsClienteClient {

  @GetMapping(value = "api/clientes/{id}")
  ClienteResponse getClienteByClienteId(@PathVariable("id") String id);
}
