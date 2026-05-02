package br.ufpr.entrypoint.controller;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.ClienteRequestInputData;
import br.ufpr.entrypoint.mapper.ClienteResponseMapper;
import br.ufpr.core.ports.input.FindClientesFromIdListInputPort;
import br.ufpr.core.ports.input.GetClienteInputPort;
import br.ufpr.core.ports.input.SaveClienteInputPort;
import br.ufpr.entrypoint.mapper.ClienteRequestMapper;
import br.ufpr.entrypoint.request.ClienteRequest;
import br.ufpr.model.request.ClienteTransferRequest;
import br.ufpr.entrypoint.response.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MS-CLIENTE

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clientes")
public class ClienteController {

  private final SaveClienteInputPort saveClienteInputPort;
  private final GetClienteInputPort getClienteInputPort;
  private final FindClientesFromIdListInputPort findClientesFromIdListInputPort;

  private final ClienteRequestMapper clienteRequestMapper;
  private final ClienteResponseMapper clienteResponseMapper;

  @Autowired
  private final RabbitTemplate rabbitTemplate;

  @PostMapping(value = "/autocadastro")
  public ResponseEntity<Void> cadastrar(@Valid @RequestBody ClienteRequest request){

      Cliente cliente = clienteRequestMapper.toDomain(request);

      saveClienteInputPort.execute(cliente);

      return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ClienteResponse> getByClienteId(@Valid @RequestBody ClienteTransferRequest request){

    ClienteRequestInputData inputData = new ClienteRequestInputData(request.getClienteId());

    Cliente cliente = getClienteInputPort.get(inputData);

    ClienteResponse clienteResponse = clienteResponseMapper.toResponse(cliente);

    return ResponseEntity.ok(clienteResponse);
  }

  @PostMapping(value = "/lista-clientes-por-id")
  public ResponseEntity<List<ClienteResponse>> batchSearchClientes(@RequestBody List<String> clienteIds){

    System.out.println("Rota de clientes acionada");

    List<Cliente> clientes = findClientesFromIdListInputPort.find(clienteIds);

    System.out.println("Clientes encontrados: " + clientes.size());

    List<ClienteResponse> responseList = clientes.stream().map(clienteResponseMapper::toResponse).toList();

    System.out.println("Retornando lista de clientes");

    return ResponseEntity.ok(responseList);
  }


}
