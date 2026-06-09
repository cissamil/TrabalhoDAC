package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.DeleteUserByUserIdOutputPort;
import br.ufpr.dataprovider.client.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserByUserIdAdapter implements DeleteUserByUserIdOutputPort {

  private final UsuarioRepository usuarioRepository;

  @Override
  public void delete(String userId) {

    usuarioRepository.deleteByUserId(userId);

    System.out.println("Credencial deletada com sucesso!");
  }
}
