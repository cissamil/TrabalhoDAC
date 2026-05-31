package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.domain.RemoveGerenteInputData;
import br.ufpr.core.ports.input.*;
import br.ufpr.entrypoint.mapper.GerenteRequestMapper;
import br.ufpr.entrypoint.request.AddGerenteRequest;
import br.ufpr.entrypoint.request.RemoveGerenteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import br.ufpr.entrypoint.response.GerenteResponse;
import br.ufpr.entrypoint.mapper.GerenteResponseMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gerentes")
public class GerenteController {

  private final GerenteRequestMapper gerenteRequestMapper;
  private final GerenteResponseMapper gerenteResponseMapper;

  private final FindGerentesInputPort findGerentesInputPort;
  private final RemoveGerenteInputPort removeGerenteInputPort;
  private final InsertNewGerenteInputPort insertNewGerenteInputPort;
  private final FindGerenteByGerenteIdInputPort findGerenteByGerenteIdInputPort;
  private final FindGerentesFromIdListInputPort findGerentesFromIdListInputPort;


  @GetMapping
  ResponseEntity<List<GerenteResponse>> getGerentes(){

    List<Gerente> gerentes = findGerentesInputPort.find();

    List<GerenteResponse> responseList = gerentes.stream().map(gerenteResponseMapper::toResponse).toList();

    return ResponseEntity.ok(responseList);
  }
  @PostMapping(value = "/adicionar-gerente")
  ResponseEntity<Void> insertGerente(@Valid @RequestBody AddGerenteRequest request){

    GerenteInputData inputData = gerenteRequestMapper.toInputData(request);

    insertNewGerenteInputPort.execute(inputData);

    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/remover-gerente")
  ResponseEntity<Void> removeGerente(@Valid @RequestBody RemoveGerenteRequest request){

    RemoveGerenteInputData inputData = new RemoveGerenteInputData();

    inputData.setGerenteId(request.getGerenteId());


    removeGerenteInputPort.execute(inputData);

    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/{gerenteId}")
  ResponseEntity<GerenteResponse> getGerenteByGerenteId(@PathVariable("gerenteId") String gerenteId){

    Gerente gerente = findGerenteByGerenteIdInputPort.find(gerenteId);

    GerenteResponse response = gerenteResponseMapper.toResponse(gerente);

    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/lista-gerentes-por-id")
  public ResponseEntity<List<GerenteResponse>> batchSearchGerentes(@RequestBody List<String> gerenteIds){

    System.out.println("Rota de gerentes acionada");

    List<Gerente> gerentes = findGerentesFromIdListInputPort.find(gerenteIds);

    System.out.println("Gerentes encontrados: " + gerentes.size());

    List<GerenteResponse> responseList = gerentes.stream().map(gerenteResponseMapper::toResponse).toList();

    System.out.println("Retornando lista de gerentes");

    return ResponseEntity.ok(responseList);
  }
}
