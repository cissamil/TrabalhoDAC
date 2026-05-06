package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.PendingClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//MS-COMPOSITION

@FeignClient(name = "MsCliente", url = "${endpoints.cliente}")
public interface MsClienteClient {

  @PostMapping(value = "/lista-clientes-por-id")
  List<PendingClienteResponse> consultClientesListFromIds(@RequestBody List<String> clienteIds);

  @GetMapping(value = "/{clienteId}")
  ClienteResponse consultClienteByClienteId(@PathVariable("clienteId") String clienteId);

}
