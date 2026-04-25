package br.ufpr.entrypoint.controller;

import br.ufpr.config.RabbitMQConfigMsCliente;
import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.ClienteRequestInputData;
import br.ufpr.core.mapper.ClienteResponseMapper;
import br.ufpr.core.ports.input.GetClienteInputPort;
import br.ufpr.core.ports.input.SaveClienteInputPort;
import br.ufpr.entrypoint.mapper.ClienteRequestMapper;
import br.ufpr.entrypoint.request.ClienteRequest;
import br.ufpr.model.request.ClienteTransferRequest;
import br.ufpr.model.response.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clientes")
public class ClienteController {

  private final SaveClienteInputPort saveClienteInputPort;
  private final GetClienteInputPort getClienteInputPort;
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
  public ClienteResponse getByClienteId(@Valid @RequestBody ClienteTransferRequest request){

    ClienteRequestInputData inputData = new ClienteRequestInputData(request.getClienteId());

    Cliente cliente = getClienteInputPort.get(inputData);

    ClienteResponse clienteResponse = clienteResponseMapper.toResponse(cliente);

    return clienteResponse;
  }

  @GetMapping(value = "/sendMessage")
  public void getByClienteId(){

    try{
    rabbitTemplate.convertAndSend(RabbitMQConfigMsCliente.REGISTER_EXCHANGE, "fluxo.autocadastro.key", "Olá MS-CONTA, como vai");
    System.out.println("Mensagem enviada para o MS-CONTA");
    }catch (Exception e){
      throw  new RuntimeException("Erro aqui pô " + e);
    }



  }

}
