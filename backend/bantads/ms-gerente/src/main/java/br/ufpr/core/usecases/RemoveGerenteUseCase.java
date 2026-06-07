package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.RemoveGerenteInputData;
import br.ufpr.core.domain.TipoGerente;
import br.ufpr.core.ports.input.RemoveGerenteInputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.core.ports.output.FindGerentesOutputPort;
import br.ufpr.core.ports.output.PublishRemoveGerenteEventOutputPort;
import br.ufpr.core.ports.output.RemoveGerenteByGerenteIdOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RemoveGerenteUseCase implements RemoveGerenteInputPort {

  private final FindGerentesOutputPort findGerentesOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;
  private final RemoveGerenteByGerenteIdOutputPort removeGerenteByGerenteIdOutputPort;
  private final PublishRemoveGerenteEventOutputPort publishRemoveGerenteEventOutputPort;

  @Override
  public void execute(RemoveGerenteInputData inputData) {

    validateDeletionAvailability();

    String gerenteId = inputData.getGerenteId();

    validateGerenteId(gerenteId);

    System.out.println("[USECASE] DELETANDO CONTA DO GERENTE");

    removeGerenteByGerenteIdOutputPort.remove(gerenteId);
    publishRemoveGerenteEventOutputPort.publish(gerenteId);
  }

  private void validateDeletionAvailability() {
    List<Gerente> gerentesList = findGerentesOutputPort.find();

    boolean isGerenteDeletionAllowed = gerentesList.size() > 1;

    if(!isGerenteDeletionAllowed){
      throw new BusinessRuleException("Não é possível deletar o último gerente do banco");
    }
  }

  private void validateGerenteId(String gerenteId) {
    boolean gerenteExists = findGerenteByGerenteIdOutputPort.exists(gerenteId);

    if (!gerenteExists){
      throw new ResourceNotFoundException("Gerente não encontrado");
    }
  }
}
