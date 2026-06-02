package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.TransferContasToGerenteInputData;
import br.ufpr.core.ports.output.*;
import infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferContasToNewGerenteUseCase implements TransferContasToNewGerenteInputPort {

  private final SaveContasOutputPort saveContasOutputPort;
  private final FindContasByGerenteIdOutputPort findContasByGerenteIdOutputPort;
  private final FindGerenteWithFewerClientesIdOutputPort findGerenteWithFewerClientesIdOutputPort;

  @Override
  public void execute(TransferContasToGerenteInputData inputData) {
    String gerenteId = inputData.getGerenteId();

    String gerentesWithFewerClientesId = findGerenteWithFewerClientesIdOutputPort.find();

    validateGerenteWithFewerClientesId(gerentesWithFewerClientesId);

    List<Conta> newContasForGerente = findContasByGerenteIdOutputPort.find(gerenteId);

    validateContasForNewGerente(newContasForGerente);

    newContasForGerente.forEach(conta -> conta.setGerenteId(gerentesWithFewerClientesId));

    saveContasOutputPort.save(newContasForGerente);
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
