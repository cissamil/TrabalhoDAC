package br.ufpr.entrypoint.controller;

import br.ufpr.core.ports.input.FindPendingContasByGerenteInputPort;
import br.ufpr.core.usecases.FindPendingContasByGerenteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  private final FindPendingContasByGerenteInputPort findPendingContasByGerenteInputPort;

  @GetMapping(value = "/contas-pendentes")
  public ResponseEntity <?> listarContasPendentes(@RequestHeader(value = "X-Gerente-Id", required = true) String gerenteId){

    System.out.println("Buscando contas pendentes para o gerente ID: " + gerenteId);

    findPendingContasByGerenteInputPort.find(gerenteId);

    return ResponseEntity.ok("Sucesso! ID lido direto do Header: " + gerenteId);


  }
}
