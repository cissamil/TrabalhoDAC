package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.*;
import br.ufpr.entrypoint.mapper.ContaResponseMapper;
import br.ufpr.entrypoint.request.*;
import br.ufpr.entrypoint.response.ContaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MS-CONTA

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/contas")
public class ContaController {

  private final ContaResponseMapper mapper;
  private final FindPendingContasInputPort findPendingContasInputPort;
  private final RefusePendingContaInputPort refusePendingContaInputPort;
  private final ApprovePendingContaInputPort approvePendingContaInputPort;
  private final FindContaByClienteIdInputPort findContaByClienteIdInputPort;
  private final DepositValueOnContaInputPort depositValueOnContaInputPort;
  private final WithDrawValueOfContaInputPort withDrawValueFromContaInputPort;
  private final TransferMoneyToAnotherContaInputPort transferMoneyToAnotherContaInputPort;

  @GetMapping(value = "/pendentes")
  ResponseEntity<List<ContaResponse>> findPendingContas(@RequestHeader("X-Gerente-Id") String gerenteId){

    System.out.println("Rota de contas acionada");

    List<Conta> contas = findPendingContasInputPort.find(gerenteId);

    List<ContaResponse> responseList = contas.stream()
      .map(mapper::toResponse)
      .toList();

    System.out.println("Retornando lista de contas");

    return ResponseEntity.ok(responseList);
  }

  @PostMapping("/{id}/aprovar")
  public ResponseEntity<Void> approveConta(@PathVariable("id") Integer contaId, @RequestBody ApproveContaRequest request){

      System.out.println("Id da conta: " + contaId + "Salario: " + request.getClienteSalario());

      ApprovePendingContaInputData inputData = new ApprovePendingContaInputData();

      inputData.setContaId(contaId);
      inputData.setClienteSalario(request.getClienteSalario());

      approvePendingContaInputPort.execute(inputData);

      return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/rejeitar")
  public ResponseEntity<Void> refuseConta(@PathVariable("id") Integer contaId, @RequestBody RefuseContaRequest request){

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

    return ResponseEntity.ok(mapper.toResponse(conta));
  }

  @PostMapping("/{id}/depositar")
  ResponseEntity<Void> depositValueOnConta(@RequestBody DepositValueRequest request){

    DepositValueInputData inputData = new DepositValueInputData();

    inputData.setContaNumber(request.getContaNumber());
    inputData.setValue(request.getValue());

    depositValueOnContaInputPort.execute(inputData);

    return ResponseEntity.noContent().build();

  }

  @PostMapping("/{id}/sacar")
  ResponseEntity<Void> withdrawValueOfConta(@RequestBody WithdrawValueRequest request){

    WithdrawValueInputData inputData = new WithdrawValueInputData();

    inputData.setContaNumber(request.getContaNumber());
    inputData.setValue(request.getValue());

    withDrawValueFromContaInputPort.execute(inputData);
    return ResponseEntity.noContent().build();


  }

  @PostMapping("/{id}/transferir")
  ResponseEntity<Void> transferMoneyToAnotherConta(@RequestBody TransferValueRequest request){

    TransferValueInputData inputData = new TransferValueInputData();

    inputData.setOriginContaNumber(request.getOriginContaNumber());
    inputData.setDestinyContaNumber(request.getDestinyContaNumber());
    inputData.setValue(request.getValue());

    transferMoneyToAnotherContaInputPort.execute(inputData);
    return ResponseEntity.noContent().build();

  }







}
