package br.ufpr.core.usecases;

import br.ufpr.core.domain.RemoveGerenteInputData;
import br.ufpr.core.ports.input.RemoveGerenteInputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.core.ports.output.FindGerentesOutputPort;
import br.ufpr.core.ports.output.PublishRemoveGerenteEventOutputPort;
import br.ufpr.core.ports.output.RemoveGerenteByGerenteIdOutputPort;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveGerenteUseCase implements RemoveGerenteInputPort {

  private final FindGerentesOutputPort findGerentesOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;
  private final RemoveGerenteByGerenteIdOutputPort removeGerenteByGerenteIdOutputPort;
  private final PublishRemoveGerenteEventOutputPort publishRemoveGerenteEventOutputPort;

  // @TODO IMPEDIR QUE O ÚLTIMO GERENTE DO BANCO SEJA REMOVIDO

  @Override
  public void execute(RemoveGerenteInputData inputData) {

    // @TODO validar se o usuário tem a permissão para remover gerentes, verificando se o token dele é token de admin (HEADER)
    String gerenteId = inputData.getGerenteId();

    validateGerenteId(gerenteId);

    System.out.println("[USECASE] DELETANDO CONTA DO GERENTE");

    removeGerenteByGerenteIdOutputPort.remove(gerenteId);
    publishRemoveGerenteEventOutputPort.publish(gerenteId);
  }

  private void validateGerenteId(String gerenteId) {
    boolean gerenteExists = findGerenteByGerenteIdOutputPort.exists(gerenteId);

    if (!gerenteExists){
      throw new ResourceNotFoundException("Gerente não encontrado");
    }
  }
}
