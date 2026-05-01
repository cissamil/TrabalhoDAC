package br.ufpr.entrypoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  @GetMapping(value = "/contas-pendentes")
  public ResponseEntity <?> listarContasPendentes(@RequestHeader(value = "X-Gerente-Id", required = true) String gerenteId){

    System.out.println("Buscando contas pendentes para o gerente ID: " + gerenteId);

    return ResponseEntity.ok("Sucesso! ID lido direto do Header: " + gerenteId);
  }
}
