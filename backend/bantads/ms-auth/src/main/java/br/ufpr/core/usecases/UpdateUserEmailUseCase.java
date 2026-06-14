package br.ufpr.core.usecases;

import br.ufpr.core.domain.UpdateUserEmailInputData;
import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.input.UpdateUserEmailInputPort;
import br.ufpr.core.ports.output.FindByUserIdOutputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailUseCase implements UpdateUserEmailInputPort {

  private final FindByUserIdOutputPort findByUserIdOutputPort;
  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;

  @Override
  public void execute(UpdateUserEmailInputData inputData) {

    String userId = inputData.getUserId();
    String userEmail = inputData.getUserNewEmail();
    String previousUserEmail = inputData.getUserPreviousEmail();

    Usuario usuario = findByUserIdOutputPort.find(userId);

    if(!usuario.getEmail().equals(previousUserEmail)){

      throw new BusinessRuleException("Email antigo do usuário não coincide com email cadastrado");
    }

    usuario.setEmail(userEmail);

    saveUsuarioCredentialOutputPort.save(usuario);

    System.out.println("Email atualizado com sucesso!");
  }
}
