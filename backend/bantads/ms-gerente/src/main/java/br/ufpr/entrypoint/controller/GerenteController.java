package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Gerente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import br.ufpr.entrypoint.response.GerenteResponse;
import br.ufpr.entrypoint.mapper.GerenteResponseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.ufpr.core.ports.input.FindGerenteByGerenteIdInputPort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gerentes")
public class GerenteController {

  private final FindGerenteByGerenteIdInputPort findGerenteByGerenteIdInputPort;
  private final GerenteResponseMapper gerenteResponseMapper;

  @GetMapping(value = "/{gerenteId}")
  ResponseEntity<GerenteResponse> getGerenteByGerenteId(@PathVariable("gerenteId") String gerenteId){

    Gerente gerente = findGerenteByGerenteIdInputPort.find(gerenteId);

    GerenteResponse response = gerenteResponseMapper.toResponse(gerente);

    return ResponseEntity.ok(response);

  }
}
