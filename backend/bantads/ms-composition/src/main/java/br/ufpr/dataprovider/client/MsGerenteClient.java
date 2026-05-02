package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.GerenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MsGerente", url = "${endpoints.gerente}")
public interface MsGerenteClient {

  @GetMapping(value = "/{id}")
  GerenteResponse consultGerente(@PathVariable("id") String gerenteId);
}
