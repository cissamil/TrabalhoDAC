package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.PrepareClienteCredentialInputPort;
import br.ufpr.core.ports.input.CreateUserCredentialInputPort;
import br.ufpr.core.ports.output.ConsultClienteOutputPort;
import br.ufpr.core.ports.output.SendEmailOutputPort;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PrepareClienteCredentialUseCase implements PrepareClienteCredentialInputPort {

  //@TODO MUDAR (SE DER TEMPO) O ENVIO DE EMAILS PARA O MS-EMAIL

  private final SendEmailOutputPort sendEmailOutputPort;
  private final ConsultClienteOutputPort consultClienteOutputPort;
  private final CreateUserCredentialInputPort createUserCredentialInputPort;

  @Override
  public void execute(TransferClienteIdInputData inputData) {

    String clienteId = inputData.getClienteId();
    ClienteOutputData clienteOutputData = consultClienteOutputPort.consult(clienteId);

    String clienteEmail = clienteOutputData.getClienteEmail();

    validateEmailCliente(clienteEmail);

    UserInputData userInputData = new UserInputData();

    String senhaUsuario = generateNewSenha();

    userInputData.setUserId(clienteId);
    userInputData.setSenha(senhaUsuario);
    userInputData.setEmail(clienteEmail);
    userInputData.setTipoUsuario(TipoUsuario.CLIENTE);

    createUserCredentialInputPort.execute(userInputData);
    sendPasswordEmailToCliente(clienteEmail, senhaUsuario);
  }

  private static void validateEmailCliente(String clienteEmail) {
    if(clienteEmail == null){
      throw new ResourceNotFoundException("Usuário não encontrado");
    }
  }

  private String generateNewSenha(){

    String characters = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz0123456789";
    StringBuilder senha = new StringBuilder();

    SecureRandom random = new SecureRandom();

    for(int i = 0; i < 5; i++){
      int index = random.nextInt(characters.length());
      senha.append(characters.charAt(index));
    }

    return senha.toString();
  }

  public void sendPasswordEmailToCliente(String clienteEmail, String password){

    String subject = "Sua conta bancária foi aprovada!";
    String message = "Olá! Sua conta bancária foi criada com sucesso! Sua senha de acesso é: " + password;

    sendEmailOutputPort.send(clienteEmail, subject, message);

  }
}
