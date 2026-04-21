package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.SaveClientePortIn;
import br.ufpr.entrypoint.mapper.ClienteMapper;
import br.ufpr.entrypoint.request.ClienteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clientes")
public class ClienteController {

  private final SaveClientePortIn saveClientePortIn;
  private final ClienteMapper mapper;

  @PostMapping(value = "/autocadastro")
  public ResponseEntity<String> cadastrar(@Valid @RequestBody ClienteRequest request){

      Cliente cliente = mapper.toDomain(request);

      saveClientePortIn.execute(cliente);


      return ResponseEntity.ok("Solicitação de cadastro enviada com sucesso! Aguarde a aprovação do gerente");

  }

}
