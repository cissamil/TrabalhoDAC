package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreatePendingContaInputPort;
import br.ufpr.core.ports.output.FindGerenteWithFewerClientesIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreatePendingConta implements CreatePendingContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindGerenteWithFewerClientesIdOutputPort findGerenteWithFewerClientesIdOutputPort;

  @Override
  public void execute(TransferClienteDataInputData inputData) {

    System.out.println("Criando conta do usuário");

    Conta novaConta = new Conta();

    Date today = new Date();

    String clienteId = inputData.getClienteId();
    String gerenteId = findGerenteWithFewerClientesIdOutputPort.find();

    validateGerenteId(gerenteId);

    novaConta.setId(null);
    novaConta.setContaId(UUID.randomUUID().toString());
    novaConta.setDataCriacao(today);
    novaConta.setClienteId(clienteId);
    novaConta.setGerenteId(gerenteId);
    novaConta.setStatusConta(StatusConta.CONTA_PENDENTE);

    saveContaOutputPort.save(novaConta);

    System.out.println("Conta criada com sucesso!");

  }

  private static void validateGerenteId(String gerenteId) {

    System.out.println("Id do gerente atribuido: " + gerenteId);
    if(gerenteId == null) {
      throw new ResourceNotFoundException("Gerente não encontrado");
    }
  }
}

