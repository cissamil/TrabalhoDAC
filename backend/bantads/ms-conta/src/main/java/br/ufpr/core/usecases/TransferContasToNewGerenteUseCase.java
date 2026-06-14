package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.TransferContasToGerenteInputData;
import br.ufpr.core.ports.output.*;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferContasToNewGerenteUseCase implements TransferContasToNewGerenteInputPort {

  private final SaveContasOutputPort saveContasOutputPort;
  private final FindContasByGerenteIdOutputPort findContasByGerenteIdOutputPort;
  private final PublishBatchSyncContaEventOutputPort publishBatchSyncContaEventOutputPort;
  private final FindGerenteWithFewerClientesIdOutputPort findGerenteWithFewerClientesIdOutputPort;
  private final PublishContasTransferidasEventOutputPort publishContasTransferidasEventOutputPort;

  @Override
  public void execute(TransferContasToGerenteInputData inputData) {

    String gerenteId = inputData.getGerenteId();

    if(gerenteId == null){
      System.out.println("Id de gerente não fornecido. Abortando...");
      return;
    }

    System.out.println("Transferindo contas do gerente: " + gerenteId);

    String gerenteWithFewerClientesId = findGerenteWithFewerClientesIdOutputPort.findWithoutSelectedGerente(gerenteId);

    System.out.println("Gerente selecionado: " + gerenteWithFewerClientesId);

    validateGerenteWithFewerClientesId(gerenteWithFewerClientesId);

    List<Conta> newContasForGerente = findContasByGerenteIdOutputPort.find(gerenteId);

    System.out.println("Quantidade de contas a serem transferidas: " + newContasForGerente.size());

    validateContasForNewGerente(newContasForGerente);

    newContasForGerente.forEach(conta -> conta.setGerenteId(gerenteWithFewerClientesId));

    saveContasOutputPort.save(newContasForGerente);
    publishContasTransferidasEventOutputPort.publish(gerenteId);
    publishBatchSyncContaEventOutputPort.publish(newContasForGerente);
  }

  private void validateContasForNewGerente(List<Conta> contas) {
    if(contas == null){
      throw new ResourceNotFoundException("Lista de contas não encontrada");
    }
  }

  private void validateGerenteWithFewerClientesId(String gerenteIdWithFewerClientes) {
    if(gerenteIdWithFewerClientes == null){
      throw new ResourceNotFoundException("Gerente não encontrado");
    }
  }
}
