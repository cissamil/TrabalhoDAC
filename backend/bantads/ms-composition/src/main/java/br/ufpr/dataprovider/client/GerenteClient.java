package br.ufpr.dataprovider.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MsGerente", url = "${endpoints.gerente}")
public interface GerenteClient {
}
