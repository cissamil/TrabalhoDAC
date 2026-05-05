package br.ufpr.core.usecases;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.TransferClienteIdInputData;
import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.input.CreateClienteCredentialInputPort;
import br.ufpr.core.ports.output.ConsultClienteOutputPort;
import br.ufpr.core.ports.output.SaveUsuarioCredentialOutputPort;
import br.ufpr.core.domain.TipoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class CreateClienteCredentialUseCase implements CreateClienteCredentialInputPort {

  @Autowired
  private final PasswordEncoder passwordEncoder;

  private final ConsultClienteOutputPort consultClienteOutputPort;
  private final SaveUsuarioCredentialOutputPort saveUsuarioCredentialOutputPort;

  @Override
  public void execute(TransferClienteIdInputData inputData) {

    String clienteId = inputData.getClienteId();
    ClienteOutputData clienteOutputData = consultClienteOutputPort.consult(clienteId);

    String clienteEmail = clienteOutputData.getClienteEmail();

    validateEmailCliente(clienteEmail);

    Usuario newUsuario = new Usuario();

    String senhaUsuario = generateNewSenha();

    newUsuario.setUserId(clienteId);
    newUsuario.setEmail(clienteEmail);
    newUsuario.setSenha(senhaUsuario);
    newUsuario.setTipoUsuario(TipoUsuario.CLIENTE);

    saveUsuarioCredentialOutputPort.save(newUsuario);

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

    // @TODO CRIPTOGRAFAR AS SENHAS ANTES DE SALVAR NO BANCO. MANTER ATÉ O LOGIN FUNCIONAR
    // return  passwordEncoder.encode(senha.toString());
    return senha.toString();

  }
}
