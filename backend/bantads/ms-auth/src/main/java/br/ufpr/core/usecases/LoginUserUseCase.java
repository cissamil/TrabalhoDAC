package br.ufpr.core.usecases;

import br.ufpr.core.domain.LoginInputData;
import br.ufpr.core.ports.output.GenerateAccessTokenOutputPort;
import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.input.LoginUserInputPort;
import br.ufpr.core.ports.output.FindByEmailOutputPort;
import br.ufpr.infrastructure.exceptions.ForbiddenResourceException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCase implements LoginUserInputPort {

  private final PasswordEncoder passwordEncoder;
  private final FindByEmailOutputPort findByEmailOutputPort;
  private final GenerateAccessTokenOutputPort generateAccessTokenOutputPort;
  @Override
  public String execute(LoginInputData inputData) {

    String email = inputData.getUserEmail();
    String password = inputData.getUserPassword();

    Usuario usuario = findByEmailOutputPort.find(email);

    validateUserCredential(usuario, password);

    return generateAccessTokenOutputPort.generate(usuario);
  }

  private void validateUserCredential(Usuario usuario, String password) {
    if (usuario == null) {
      throw new ResourceNotFoundException("Usuário não encontrado. Verifique o seu e-mail e tente novamente");
    }

    boolean correctPassword = passwordEncoder.matches(password, usuario.getSenha());

    if (!correctPassword) {
      throw new ForbiddenResourceException("Senha incorreta. Tente novamente");
    }
  }
}
