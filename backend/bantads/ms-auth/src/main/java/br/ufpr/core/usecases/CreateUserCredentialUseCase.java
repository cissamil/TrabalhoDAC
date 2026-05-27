package br.ufpr.core.usecases;

import br.ufpr.core.domain.Usuario;
import lombok.RequiredArgsConstructor;
import br.ufpr.core.domain.UserInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import br.ufpr.core.ports.input.CreateUserCredentialInputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;

@Component
@RequiredArgsConstructor
public class CreateUserCredentialUseCase implements CreateUserCredentialInputPort {

  @Autowired
  private final PasswordEncoder passwordEncoder;
  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;

  @Override
  public void execute(UserInputData inputData) {

    Usuario usuario = new Usuario();

    String encodedPassword = encodePassword(inputData.getSenha());

    usuario.setId(null);
    usuario.setEmail(inputData.getEmail());
    usuario.setSenha(encodedPassword);
    usuario.setUserId(inputData.getUserId());
    usuario.setTipoUsuario(inputData.getTipoUsuario());

    saveUsuarioCredentialOutputPort.save(usuario);
  }


  public String encodePassword(String password){
    return passwordEncoder.encode(password);
  }
}
