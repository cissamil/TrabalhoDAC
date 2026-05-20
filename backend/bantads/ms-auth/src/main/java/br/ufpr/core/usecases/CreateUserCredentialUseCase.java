package br.ufpr.core.usecases;

import br.ufpr.core.domain.Usuario;
import lombok.RequiredArgsConstructor;
import br.ufpr.core.domain.UserInputData;
import org.springframework.stereotype.Component;
import br.ufpr.core.ports.input.CreateUserCredentialInputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;

@Component
@RequiredArgsConstructor
public class CreateUserCredentialUseCase implements CreateUserCredentialInputPort {

  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;

  @Override
  public void execute(UserInputData inputData) {

    Usuario usuario = new Usuario();

    usuario.setId(null);
    usuario.setEmail(inputData.getEmail());
    usuario.setSenha(inputData.getSenha());
    usuario.setUserId(inputData.getUserId());
    usuario.setTipoUsuario(inputData.getTipoUsuario());

    saveUsuarioCredentialOutputPort.save(usuario);
  }
}
