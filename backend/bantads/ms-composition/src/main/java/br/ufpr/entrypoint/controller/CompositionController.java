package br.ufpr.entrypoint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class CompositionController {

  @GetMapping(value = "/contas-pendentes")
  public ResponseEntity <String> listarContasPendentes(@RequestAttribute(value = "usuarioLogadoId", required = false) String idGerente){

    if(idGerente == null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou inválido");
    }

    System.out.println("Buscando contas pendentes para o gerente ID: " + idGerente);

    return ResponseEntity.status(200).build();


  }
}
