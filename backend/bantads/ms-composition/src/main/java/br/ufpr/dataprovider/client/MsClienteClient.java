package br.ufpr.dataprovider.client;

import br.ufpr.model.response.PendingClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MsCliente", url = "${endpoints.cliente}")
public interface MsClienteClient {

  @PostMapping(value = "/busca-lote")
  List<PendingClienteResponse> consultBatchSearchClientes(@RequestBody List<String> clienteIds);

}
