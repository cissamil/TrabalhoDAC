package br.ufpr.core.usecases;

import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.input.CreateClienteCredentialInputPort;
import br.ufpr.core.ports.output.GetClienteEmailFromClienteIdOutputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;
import br.ufpr.model.enumerator.TipoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class CreateClienteCredentialUseCase implements CreateClienteCredentialInputPort {

  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;
  private final GetClienteEmailFromClienteIdOutputPort getClienteEmailFromClienteIdOutputPort;

  @Override
  public void execute(TransferClienteIdInputData inputData) {

    String clienteId = inputData.getClienteId();
    String clienteEmail = getClienteEmailFromClienteIdOutputPort.get(clienteId);
    validateEmailCliente(clienteEmail);

    Usuario newUsuario = new Usuario();

    String senhaUsuario = generateNewSenha();



    newUsuario.setLogin(clienteEmail);
    newUsuario.setSenha(senhaUsuario);
    newUsuario.setTipoUsuario(TipoUsuario.CLIENTE);


    saveUsuarioCredentialOutputPort.save(newUsuario);

    System.out.println("Credenciais criadas no MongoDB para: " + newUsuario.getLogin());

  }

  private static void validateEmailCliente(String clienteEmail) {
    if(clienteEmail == null){
      throw new RuntimeException("Usuário não encontrado");
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
}
