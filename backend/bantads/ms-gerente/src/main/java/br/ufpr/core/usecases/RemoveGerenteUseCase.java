package br.ufpr.core.usecases;

import br.ufpr.core.ports.input.RemoveGerenteInputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.core.ports.output.RemoveGerenteByGerenteIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveGerenteUseCase implements RemoveGerenteInputPort {

  private final RemoveGerenteByGerenteIdOutputPort removeGerenteByGerenteIdOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;

  @Override
  public void execute(String gerenteId) {

    boolean gerenteExists = findGerenteByGerenteIdOutputPort.exists(gerenteId);

    if(!gerenteExists){
      System.out.println("Gerente não existe mais no banco. Abortando...");
      return;
    }

    System.out.println("[USECASE] DELETANDO CONTA DO GERENTE");

    removeGerenteByGerenteIdOutputPort.remove(gerenteId);
  }
}
