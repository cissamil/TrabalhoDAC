package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.*;
import br.ufpr.dataprovider.mapper.MovimentacaoResponseMapper;
import br.ufpr.entrypoint.mapper.*;
import br.ufpr.entrypoint.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  private final GroupClientesReportInputPort groupClientesReportInputPort;
  private final GroupClientesContasInputPort groupClientesContasInputPort;
  private final ClienteContaDashboardAssembler clienteContaDashboardMapper;
  private final GroupGerenteStatisticsInputPort groupGerenteStatisticsInputPort;
  private final PendingContasDashboardAssembler pendingContasDashboardAssembler;
  private final ClientesReportDashboardAssembler clientesReportDashboardAssembler;
  private final ClientesContasDashboardAssembler clientesContasDashboardAssembler;
  private final GroupClienteContaGerenteInputPort groupClienteContaGerenteInputPort;
  private final GroupClienteMovimentacoesInputPort groupClienteMovimentacoesInputPort;
  private final GerenteStatisticsDashboardAssembler gerenteStatisticsDashboardAssembler;
  private final GroupPendingContasDashboardInputPort groupPendingContasDashboardInputPort;
  private final LargestBalanceContaDashboardAssembler largestBalanceContaDashboardAssembler;
  private final ClienteMovimentacoesDashboardAssembler clienteMovimentacoesDashboardAssembler;
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

  @GetMapping(value = "/consultar-clientes")
  public ResponseEntity<List<ClientesContasDashboardResponse>> clientesConsult(@RequestHeader("X-Gerente-Id") String gerenteId){

    ClientesContasDashboardOutputData outputData = groupClientesContasInputPort.execute(gerenteId);

    List<ClientesContasDashboardResponse> response = clientesContasDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/consultar-extrato")
  public ResponseEntity<ClienteMovimentacoesDashboardResponse> consultBankStatement(
    @RequestHeader("X-Cliente-Id") String clienteId,
    @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
    @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
  ){

    System.out.println("Pegando o extrato do cliente: " + clienteId);
    System.out.println("Data Início: " + dataInicio + " Data Fim: " + dataFim);


    ConsultBankStatementInputData inputData = new ConsultBankStatementInputData();

    inputData.setClienteId(clienteId);
    inputData.setDataInicio(dataInicio);
    inputData.setDataFim(dataFim);

    ClienteMovimentacoesDashboardOutputData outputData = groupClienteMovimentacoesInputPort.execute(inputData);

    ClienteMovimentacoesDashboardResponse response = clienteMovimentacoesDashboardAssembler.assemble(outputData);

    System.out.println("Response: " + response);

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/cliente-conta")
  public ResponseEntity <ClienteContaDashboardResponse> groupClienteAndConta(@RequestHeader(value = "X-Cliente-Id", required = true) String clienteId){

    System.out.println("Buscando Perfil e Conta do Cliente: " + clienteId);

    ClienteContaDashboardOutputData outputData = groupClienteContaGerenteInputPort.execute(clienteId);

    ClienteContaDashboardResponse response = clienteContaDashboardMapper.assemble(outputData);

    return ResponseEntity.ok(response);

  }

  @GetMapping(value = "/dashboard-admin")
  public ResponseEntity<List<GerenteStatisticsDashboardResponse>> groupGerentesStatistics(){

    GerentesStatisticsDashboardOutputData outputData = groupGerenteStatisticsInputPort.execute();

    List<GerenteStatisticsDashboardResponse> response = gerenteStatisticsDashboardAssembler.assemble(outputData);

    return ResponseEntity.ok(response);

  }
}
