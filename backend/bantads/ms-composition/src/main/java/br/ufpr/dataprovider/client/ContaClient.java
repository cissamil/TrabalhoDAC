package br.ufpr.dataprovider.client;

import br.ufpr.model.response.PendingClienteResponse;
import br.ufpr.model.response.PendingPedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "MsConta", url = "${endpoints.conta}")
public interface ContaClient {

  @GetMapping(value = "/pedidos")
  List<PendingPedidoResponse> pedidos(@RequestHeader("X-Gerente-Id") String gerenteId);


}
