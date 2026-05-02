package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.PendingContasDashboardOutputData;
import br.ufpr.core.ports.input.BuildPendingContasDashboardInputPort;
import br.ufpr.entrypoint.mapper.PendingContasDashboardAssembler;
import br.ufpr.entrypoint.response.PendingContasDashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  private final BuildPendingContasDashboardInputPort buildPendingContasDashboardInputPort;
  private final PendingContasDashboardAssembler pendingContasDashboardAssembler;

  @GetMapping(value = "/contas-pendentes")
  public ResponseEntity <?> listarContasPendentes(@RequestHeader(value = "X-Gerente-Id", required = true) String gerenteId){

    System.out.println("Buscando contas pendentes para o gerente ID: " + gerenteId);

    PendingContasDashboardOutputData outputData = buildPendingContasDashboardInputPort.execute(gerenteId);

    PendingContasDashboardResponse dashboardResponse = pendingContasDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(dashboardResponse);

  }
}
