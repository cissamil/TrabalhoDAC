package br.ufpr.core.usecases;

import br.ufpr.core.ports.input.DeleteUserCredentialInputPort;
import br.ufpr.core.ports.output.DeleteUserByUserIdOutputPort;
import br.ufpr.core.ports.output.FindByUserIdOutputPort;
import br.ufpr.core.ports.output.PublishUserCredentialDeletedEventOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserCredentialUseCase implements DeleteUserCredentialInputPort {

  private final FindByUserIdOutputPort findByUserIdOutputPort;
  private final DeleteUserByUserIdOutputPort deleteUserByUserIdOutputPort;
  private final PublishUserCredentialDeletedEventOutputPort publishUserCredentialDeletedEventOutputPort;

  @Override
  public void delete(String userId) {

    boolean userExists = findByUserIdOutputPort.exists(userId);

    if(!userExists){
      System.out.println("Usuário não existe no banco. Abortando...");
      return;
    }

    deleteUserByUserIdOutputPort.delete(userId);

    publishUserCredentialDeletedEventOutputPort.publish(userId);

  }
}
