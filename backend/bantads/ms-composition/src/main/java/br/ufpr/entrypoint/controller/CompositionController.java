package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.ClienteContaDashboardOutputData;
import br.ufpr.core.domain.ClientesReportDashboardOutputData;
import br.ufpr.core.domain.LargestBalanceContasDashboardOutputData;
import br.ufpr.core.domain.PendingContasDashboardOutputData;
import br.ufpr.core.ports.input.GroupClienteContaGerenteInputPort;
import br.ufpr.core.ports.input.GroupClientesReportInputPort;
import br.ufpr.core.ports.input.GroupLargestBalanceContasWIthClienteInputPort;
import br.ufpr.core.ports.input.GroupPendingContasDashboardInputPort;
import br.ufpr.entrypoint.mapper.ClienteContaDashboardAssembler;
import br.ufpr.entrypoint.mapper.ClientesReportDashboardAssembler;
import br.ufpr.entrypoint.mapper.LargestBalanceContaDashboardAssembler;
import br.ufpr.entrypoint.mapper.PendingContasDashboardAssembler;
import br.ufpr.entrypoint.response.ClienteContaDashboardResponse;
import br.ufpr.entrypoint.response.ClientesReportDashboardResponse;
import br.ufpr.entrypoint.response.LargestBalanceContaDashboardResponse;
import br.ufpr.entrypoint.response.PendingContasDashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  private final GroupClientesReportInputPort groupClientesReportInputPort;
  private final ClienteContaDashboardAssembler clienteContaDashboardMapper;
  private final PendingContasDashboardAssembler pendingContasDashboardAssembler;
  private final ClientesReportDashboardAssembler clientesReportDashboardAssembler;
  private final GroupClienteContaGerenteInputPort groupClienteContaGerenteInputPort;
  private final GroupPendingContasDashboardInputPort groupPendingContasDashboardInputPort;
  private final LargestBalanceContaDashboardAssembler largestBalanceContaDashboardAssembler;
  private final GroupLargestBalanceContasWIthClienteInputPort groupLargestBalanceContasWIthClienteInputPort;

  @GetMapping(value = "/contas-pendentes")
  public ResponseEntity <PendingContasDashboardResponse> listPendingContas(@RequestHeader(value = "X-Gerente-Id", required = true) String gerenteId){

    System.out.println("Buscando contas pendentes para o gerente ID: " + gerenteId);

    PendingContasDashboardOutputData outputData = groupPendingContasDashboardInputPort.execute(gerenteId);

    PendingContasDashboardResponse dashboardResponse = pendingContasDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(dashboardResponse);

  }


  @GetMapping(value = "/melhores-clientes")
  public ResponseEntity<List<LargestBalanceContaDashboardResponse>> listBestClientes(){

    LargestBalanceContasDashboardOutputData outputData = groupLargestBalanceContasWIthClienteInputPort.execute();

    List<LargestBalanceContaDashboardResponse> responseList = largestBalanceContaDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(responseList);

  }

  @GetMapping(value = "/relatorio-clientes")
  public ResponseEntity<List<ClientesReportDashboardResponse>> clientesReports(){

    ClientesReportDashboardOutputData outputData = groupClientesReportInputPort.execute();


    List<ClientesReportDashboardResponse> response = clientesReportDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/cliente-conta")
  public ResponseEntity <ClienteContaDashboardResponse> agroupClienteAndConta(@RequestHeader(value = "X-Cliente-Id", required = true) String clienteId){


    System.out.println("Buscando Perfil e Conta do Cliente: " + clienteId);

    ClienteContaDashboardOutputData outputData = groupClienteContaGerenteInputPort.execute(clienteId);

    ClienteContaDashboardResponse response = clienteContaDashboardMapper.assemble(outputData);

    return ResponseEntity.ok(response);

  }
}
