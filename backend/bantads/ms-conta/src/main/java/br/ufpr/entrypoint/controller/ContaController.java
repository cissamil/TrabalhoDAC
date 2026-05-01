package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.ApprovePendingContaInputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.RefusePendingContaInputData;
import br.ufpr.core.ports.input.ApprovePendingContaInputPort;
import br.ufpr.core.ports.input.FindPendingContasInputPort;
import br.ufpr.core.ports.input.RefusePendingContaInputPort;
import br.ufpr.entrypoint.mapper.PendingContaResponseMapper;
import br.ufpr.entrypoint.request.ApproveContaRequest;
import br.ufpr.entrypoint.request.RefuseContaRequest;
import br.ufpr.model.response.PendingContaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// MS-CONTA

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/contas")
public class ContaController {

  private final PendingContaResponseMapper mapper;
  private final FindPendingContasInputPort findPendingContasInputPort;
  private final RefusePendingContaInputPort refusePendingContaInputPort;
  private final ApprovePendingContaInputPort approvePendingContaInputPort;

  @GetMapping(value = "/pendentes")
  ResponseEntity<List<PendingContaResponse>> findPendingContas(@RequestHeader("X-Gerente-Id") String gerenteId){

    System.out.println("Rota de contas acionada");

    List<Conta> contas = findPendingContasInputPort.find(gerenteId);

    List<PendingContaResponse> responseList = contas.stream()
      .map(mapper::toResponse)
      .toList();

    System.out.println("Retornando lista de contas");

    return ResponseEntity.ok(responseList);
  }

  @PostMapping("/{id}/aprovar")
  public ResponseEntity<?> approveConta(@PathVariable("id") Integer contaId, @RequestBody ApproveContaRequest request){

      System.out.println("Id da conta: " + contaId + "Salario: " + request.getClienteSalario());

      ApprovePendingContaInputData inputData = new ApprovePendingContaInputData();

      inputData.setContaId(contaId);
      inputData.setClienteSalario(request.getClienteSalario());

      approvePendingContaInputPort.execute(inputData);

      return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/rejeitar")
  public ResponseEntity<?> refuseConta(@PathVariable("id") Integer contaId, @RequestBody RefuseContaRequest request){

    System.out.println("Id da conta: " + contaId + " Motivo de recusa: " + request.getMotivoRecusa());

    RefusePendingContaInputData inputData = new RefusePendingContaInputData();

    inputData.setContaId(contaId);
    inputData.setMotivoRecusa(request.getMotivoRecusa());

    refusePendingContaInputPort.execute(inputData);

    return ResponseEntity.noContent().build();
  }


}
