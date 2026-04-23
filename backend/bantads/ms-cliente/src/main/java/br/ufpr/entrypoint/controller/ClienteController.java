package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.SaveClienteInputPort;
import br.ufpr.entrypoint.mapper.ClienteRequestMapper;
import br.ufpr.entrypoint.request.ClienteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clientes")
public class ClienteController {

  private final SaveClienteInputPort saveClienteInputPort;
  private final ClienteRequestMapper mapper;

  @PostMapping(value = "/autocadastro")
  public ResponseEntity<Void> cadastrar(@Valid @RequestBody ClienteRequest request){

      Cliente cliente = mapper.toDomain(request);

      saveClienteInputPort.execute(cliente);

      return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();

  }

}
