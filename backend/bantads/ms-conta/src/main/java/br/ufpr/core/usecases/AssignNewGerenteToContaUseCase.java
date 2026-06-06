package br.ufpr.core.usecases;

import br.ufpr.core.domain.AssignGerenteToContaInputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.AssignNewGerenteToContaInputPort;
import br.ufpr.core.ports.output.FindFirstContaByGerenteIdOutputPort;
import br.ufpr.core.ports.output.FindGerenteWithMostClientesIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignNewGerenteToContaUseCase implements AssignNewGerenteToContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindFirstContaByGerenteIdOutputPort findFirstContaByGerenteIdOutputPort;
  private final FindGerenteWithMostClientesIdOutputPort findGerenteIdWithMostClientesOutputPort;


  // @TODO FAZER COM QUE A CONTA ADICIONADA SEJA A COM MENOR SALDO POSITIVO CASO EMPATE
  // @TODO FAZER COM QUE O USECASE ACEITE O PRIMEIRO GERENTE SEM NENHUMA CONTA

  @Override
  public void execute(AssignGerenteToContaInputData inputData) {

    String gerenteId = inputData.getGerenteId();

    String gerenteWithMostClientesId = findGerenteIdWithMostClientesOutputPort.find();

    validateGerenteWithMostClientesId(gerenteWithMostClientesId);

    Conta contaByNewGerente = findFirstContaByGerenteIdOutputPort.find(gerenteWithMostClientesId);

    validateContaByPreviousGerente(contaByNewGerente);

    contaByNewGerente.setGerenteId(gerenteId);

    saveContaOutputPort.save(contaByNewGerente);


  }

  private void validateContaByPreviousGerente(Conta contaByPreviousGerente) {
    if(contaByPreviousGerente == null){
      throw new ResourceNotFoundException("Conta não encontrada");
    }
  }

  private void validateGerenteWithMostClientesId(String gerenteIdWithMostClientes) {
    if(gerenteIdWithMostClientes == null){
      throw new ResourceNotFoundException("Gerente não encontrado");
    }
  }
}
