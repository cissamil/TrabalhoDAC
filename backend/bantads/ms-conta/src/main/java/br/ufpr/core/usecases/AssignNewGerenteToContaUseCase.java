package br.ufpr.core.usecases;

import br.ufpr.core.domain.AssignGerenteToContaInputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.AssignNewGerenteToContaInputPort;
import br.ufpr.core.ports.output.*;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignNewGerenteToContaUseCase implements AssignNewGerenteToContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindGerenteTotalClientesOutputPort findGerenteTotalClientesOutputPort;
  private final FindGerenteWithMostClientesIdOutputPort findGerenteIdWithMostClientesOutputPort;
  private final FindContaWithMenorSaldoByGerenteIdOutputPort findContaWithMenorSaldoByGerenteIdOutputPort;

  @Override
  public void execute(AssignGerenteToContaInputData inputData) {

    List<GerenteTotalClientesOutputData> gerentesClientesRelation = findGerenteTotalClientesOutputPort.find();

    if (!isContaAllowedToAssign(gerentesClientesRelation)) return;

    String gerenteId = inputData.getGerenteId();

    String gerenteWithMostClientesId = findGerenteIdWithMostClientesOutputPort.find();

    validateGerenteWithMostClientesId(gerenteWithMostClientesId);

    System.out.println("Relação de gerentes e quantidade de clientes: " + gerentesClientesRelation);

    String gerenteToReplace = gerentesClientesRelation.get(0).getGerente();

    Conta contaToAssign = findContaWithMenorSaldoByGerenteIdOutputPort.find(gerenteToReplace);

    validateContaByPreviousGerente(contaToAssign);

    contaToAssign.setGerenteId(gerenteId);

    saveContaOutputPort.save(contaToAssign);
  }

  private static boolean isContaAllowedToAssign(List<GerenteTotalClientesOutputData> gerentesClientesRelation) {
    if(gerentesClientesRelation.isEmpty()){
      System.out.println("Primeiro gerente do banco. Sem contas a atrelar");
      return false;
    }

    List<GerenteTotalClientesOutputData> gerentesWithMoreThanOneConta =
      gerentesClientesRelation
        .stream()
        .filter(relation -> relation.getQtdClientes() > 1)
        .toList();

    if(gerentesWithMoreThanOneConta.isEmpty()){
      System.out.println("Nenhum gerente possui mais de uma conta para ser atribuida para novo gerente");
      return false;
    }

    return true;
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
