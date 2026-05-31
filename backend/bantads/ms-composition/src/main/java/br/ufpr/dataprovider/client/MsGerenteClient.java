package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.GerenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "MsGerente", url = "${endpoints.gerente}")
public interface MsGerenteClient {

  @PostMapping(value = "/lista-gerentes-por-id")
  List<GerenteResponse> consultGerentesListFromIds(@RequestBody List<String> clienteIds);

  @GetMapping(value = "/{id}")
  GerenteResponse consultGerente(@PathVariable("id") String gerenteId);

  @GetMapping
  List<GerenteResponse> consultGerentes();
}
