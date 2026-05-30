package br.ufpr.core.usecases;

import br.ufpr.core.domain.UpdateUserEmailInputData;
import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.input.UpdateUserEmailInputPort;
import br.ufpr.core.ports.output.FindByUserIdOutputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailUseCase implements UpdateUserEmailInputPort {

  private final FindByUserIdOutputPort findByUserIdOutputPort;
  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;

  @Override
  public void execute(UpdateUserEmailInputData inputData) {

    String userId = inputData.getUserId();
    String userEmail = inputData.getUserEmail();

    Usuario usuario = findByUserIdOutputPort.find(userId);

    usuario.setEmail(userEmail);

    saveUsuarioCredentialOutputPort.save(usuario);

    System.out.println("Email atualizado com sucesso!");
  }
}
