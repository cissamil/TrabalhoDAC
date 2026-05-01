package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.AprovePendingContaInputData;
import br.ufpr.core.domain.RefusePendingContaInputData;
import br.ufpr.core.ports.input.AprovePendingContaInputPort;
import br.ufpr.core.ports.input.RefusePendingContaInputPort;
import br.ufpr.entrypoint.request.AproveContaRequest;
import br.ufpr.entrypoint.request.RefuseContaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/contas")
public class ContaController {

  private final AprovePendingContaInputPort aprovePendingContaInputPort;
  private final RefusePendingContaInputPort refusePendingContaInputPort;

  @PostMapping("/{id}/aprovar")
  public ResponseEntity<?> aprovarConta(@PathVariable("id") Integer contaId, @RequestBody AproveContaRequest request){

      System.out.println("Id da conta: " + contaId + "Salario: " + request.getClienteSalario());

      AprovePendingContaInputData inputData = new AprovePendingContaInputData();

      inputData.setContaId(contaId);
      inputData.setClienteSalario(request.getClienteSalario());

      aprovePendingContaInputPort.execute(inputData);

      return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/rejeitar")
  public ResponseEntity<?> rejeitarConta(@PathVariable("id") Integer contaId, @RequestBody RefuseContaRequest request){

    System.out.println("Id da conta: " + contaId + " Motivo de recusa: " + request.getMotivoRecusa());

    RefusePendingContaInputData inputData = new RefusePendingContaInputData();

    inputData.setContaId(contaId);
    inputData.setMotivoRecusa(request.getMotivoRecusa());

    refusePendingContaInputPort.execute(inputData);

    return ResponseEntity.noContent().build();
  }
}
