package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.dataprovider.client.domain.PendingContaResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


// MS-COMPOSITION

@FeignClient(name = "MsConta", url = "${endpoints.conta}")
public interface MsContaClient {

  @GetMapping(value = "/pendentes")
  List<PendingContaResponse> consultPendingContas(@RequestHeader("X-Gerente-Id") String gerenteId);


  @GetMapping
  ContaResponse consultContaByClienteId(@RequestParam String clienteId);


}
