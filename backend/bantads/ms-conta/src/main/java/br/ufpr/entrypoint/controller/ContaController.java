package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.*;
import br.ufpr.entrypoint.mapper.ContaResponseMapper;
import br.ufpr.entrypoint.mapper.MovimentacaoResponseMapper;
import br.ufpr.entrypoint.request.*;
import br.ufpr.entrypoint.response.ContaResponse;
import br.ufpr.entrypoint.response.MovimentacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// MS-CONTA

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/contas")
public class ContaController {

  private final ContaResponseMapper contaResponseMapper;
  private final FindPendingContasInputPort findPendingContasInputPort;
  private final MovimentacaoResponseMapper movimentacaoResponseMapper;
  private final RefusePendingContaInputPort refusePendingContaInputPort;
  private final FindApprovedContasInputPort findApprovedContasInputPort;
  private final DepositValueOnContaInputPort depositValueOnContaInputPort;
  private final ApprovePendingContaInputPort approvePendingContaInputPort;
  private final FindContasByQuantityInputPort findContasByQuantityInputPort;
  private final FindContaByClienteIdInputPort findContaByClienteIdInputPort;
  private final WithDrawValueOfContaInputPort withDrawValueFromContaInputPort;
  private final TransferMoneyToAnotherContaInputPort transferMoneyToAnotherContaInputPort;
  private final FindMovimentacoesBetweenDatesInputPort findMovimentacoesBetweenDatesInputPort;

  @GetMapping(value = "/pendentes")
  ResponseEntity<List<ContaResponse>> findPendingContas(@RequestHeader("X-Gerente-Id") String gerenteId){

    System.out.println("Rota de contas acionada");

    List<Conta> contas = findPendingContasInputPort.find(gerenteId);

    List<ContaResponse> responseList = contas.stream()
      .map(contaResponseMapper::toResponse)
      .toList();

    System.out.println("Retornando lista de contas");

    return ResponseEntity.ok(responseList);
  }

  @GetMapping(value = "/aprovadas")
  ResponseEntity<List<ContaResponse>> findApprovedContas(@RequestHeader("X-Gerente-Id") String gerenteId){

    System.out.println("Rota de contas acionada. Id do gerente: '" + gerenteId + "'");

    List<Conta> contas = findApprovedContasInputPort.find(gerenteId);

    List<ContaResponse> responseList = contas.stream()
      .map(contaResponseMapper::toResponse)
      .toList();

    System.out.println("Retornando lista de contas");

    return ResponseEntity.ok(responseList);
  }

  @PostMapping("/{contaId}/aprovar")
  public ResponseEntity<Void> approveConta(@PathVariable("contaId") String contaId, @RequestBody ApproveContaRequest request){

      System.out.println("Id da conta: " + contaId + "Salario: " + request.getClienteSalario());

      ApprovePendingContaInputData inputData = new ApprovePendingContaInputData();

      inputData.setContaId(contaId);
      inputData.setClienteSalario(request.getClienteSalario());

      approvePendingContaInputPort.execute(inputData);

      return ResponseEntity.noContent().build();
  }

  @GetMapping("/movimentacoes")
  public ResponseEntity<List<MovimentacaoResponse>> findContaMovimentacoes(
    @RequestParam("clienteId") String clienteId,
    @RequestParam("dataInicio") LocalDate dataInicio, // Pode passar como String ISO
    @RequestParam("dataFim") LocalDate dataFim
  ){

    System.out.println("Data Início: " + dataInicio + "Data Fim: " + dataFim);

    ConsultBankStatementInputData inputData = new ConsultBankStatementInputData(
      clienteId,
      dataInicio,
      dataFim
    );

    List<Movimentacao> movimentacoes = findMovimentacoesBetweenDatesInputPort.find(inputData);

    List<MovimentacaoResponse> movimentacaoResponseList = movimentacoes.stream().map(movimentacaoResponseMapper::toResponse).toList();

    System.out.println("Movimentações: " + movimentacoes);
    return ResponseEntity.ok(movimentacaoResponseList);
  }

  @PostMapping("/{contaId}/rejeitar")
  public ResponseEntity<Void> refuseConta(@PathVariable("contaId") String contaId, @RequestBody RefuseContaRequest request){

    System.out.println("Id da conta: " + contaId + " Motivo de recusa: " + request.getMotivoRecusa());

    RefusePendingContaInputData inputData = new RefusePendingContaInputData();

    inputData.setContaId(contaId);
    inputData.setMotivoRecusa(request.getMotivoRecusa());

    refusePendingContaInputPort.execute(inputData);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<ContaResponse> findByClienteId(@RequestParam String clienteId){

    Conta conta = findContaByClienteIdInputPort.find(clienteId);

    return ResponseEntity.ok(contaResponseMapper.toResponse(conta));
  }

  @PostMapping("/depositar")
  ResponseEntity<Void> depositValueOnConta(@RequestBody DepositValueRequest request){

    DepositValueInputData inputData = new DepositValueInputData();

    inputData.setContaNumber(request.getContaNumber());
    inputData.setValue(request.getValue());

    depositValueOnContaInputPort.execute(inputData);

    return ResponseEntity.noContent().build();

  }

  @PostMapping("/sacar")
  ResponseEntity<Void> withdrawValueOfConta(@RequestBody WithdrawValueRequest request){

    WithdrawValueInputData inputData = new WithdrawValueInputData();

    inputData.setContaNumber(request.getContaNumber());
    inputData.setValue(request.getValue());

    withDrawValueFromContaInputPort.execute(inputData);
    return ResponseEntity.noContent().build();


  }

  @PostMapping("/transferir")
  ResponseEntity<Void> transferMoneyToAnotherConta(@RequestBody TransferValueRequest request){

    TransferValueInputData inputData = new TransferValueInputData();

    inputData.setOriginContaNumber(request.getOriginContaNumber());
    inputData.setDestinyContaNumber(request.getDestinyContaNumber());
    inputData.setValue(request.getValue());

    transferMoneyToAnotherContaInputPort.execute(inputData);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/contas-por-quantidade")
  ResponseEntity<List<ContaResponse>> findContasByQuantity(@RequestHeader("X-Quantidade") int quantity){

    List<Conta> contaList = findContasByQuantityInputPort.find(quantity);

    List<ContaResponse> responseList = contaList.stream().map(contaResponseMapper::toResponse).toList();

    return ResponseEntity.ok(responseList);
  }

}
