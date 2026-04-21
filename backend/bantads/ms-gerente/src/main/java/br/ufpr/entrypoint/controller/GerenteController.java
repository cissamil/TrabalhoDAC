package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.GerenteAdmin;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gerentes")
public class GerenteController {

  @Operation(summary = "get-gerentes", description = "Pega todos os gerentes cadastrados")
  @GetMapping(value = "/gerentes")
  public List<GerenteAdmin> getGerentes(){



  }

}
