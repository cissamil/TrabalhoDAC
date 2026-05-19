package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.ports.input.InsertNewGerenteInputPort;
import br.ufpr.entrypoint.mapper.GerenteRequestMapper;
import br.ufpr.entrypoint.request.GerenteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import br.ufpr.entrypoint.response.GerenteResponse;
import br.ufpr.entrypoint.mapper.GerenteResponseMapper;
import org.springframework.web.bind.annotation.*;
import br.ufpr.core.ports.input.FindGerenteByGerenteIdInputPort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gerentes")
public class GerenteController {

  private final GerenteRequestMapper gerenteRequestMapper;
  private final GerenteResponseMapper gerenteResponseMapper;
  private final FindGerenteByGerenteIdInputPort findGerenteByGerenteIdInputPort;
  private final InsertNewGerenteInputPort insertNewGerenteInputPort;

  @PostMapping(value = "/adicionar-gerente")
  ResponseEntity<Void> insertGerente(@Valid @RequestBody GerenteRequest request){

    GerenteInputData inputData = gerenteRequestMapper.toInputData(request);

    insertNewGerenteInputPort.execute(inputData);

    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/{gerenteId}")
  ResponseEntity<GerenteResponse> getGerenteByGerenteId(@PathVariable("gerenteId") String gerenteId){

    Gerente gerente = findGerenteByGerenteIdInputPort.find(gerenteId);

    GerenteResponse response = gerenteResponseMapper.toResponse(gerente);

    return ResponseEntity.ok(response);

  }
}
