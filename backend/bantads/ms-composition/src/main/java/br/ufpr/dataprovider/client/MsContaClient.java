package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.dataprovider.client.domain.LargestBalancesContasResponse;
import br.ufpr.dataprovider.client.domain.MovimentacaoResponse;
import br.ufpr.dataprovider.client.domain.PendingContaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


// MS-COMPOSITION

@FeignClient(name = "MsConta", url = "${endpoints.conta}")
public interface MsContaClient {

  @GetMapping(value = "/pendentes")
  List<PendingContaResponse> consultPendingContas(@RequestHeader("X-Gerente-Id") String gerenteId);

  @GetMapping(value = "/aprovadas")
  List<ContaResponse> consultApprovedContas(@RequestHeader("X-Gerente-Id") String gerenteId);

  @GetMapping(value = "/contas-por-quantidade")
  List<LargestBalancesContasResponse> consultContasByQuantity(@RequestHeader("X-Quantidade") int quantity);

  @GetMapping(value = "/movimentacoes")
  List<MovimentacaoResponse> consultContaMovimentacoes(
    @RequestParam("clienteId") String clienteId,
    @RequestParam("dataInicio") LocalDate dataInicio, // Pode passar como String ISO
    @RequestParam("dataFim") LocalDate dataFim
  );

  @GetMapping
  ContaResponse consultContaByClienteId(@RequestParam String clienteId);


}
