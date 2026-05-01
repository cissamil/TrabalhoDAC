package br.ufpr.dataprovider.client;

import br.ufpr.model.response.PendingClienteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MsCliente", url = "${endpoints.cliente}")
public interface MsClienteClient {

  @GetMapping(value = "/busca-lote")
  List<PendingClienteResponse> batchSearchClientes(@RequestParam(name = "clienteIds") List<String> clienteIds);



}
