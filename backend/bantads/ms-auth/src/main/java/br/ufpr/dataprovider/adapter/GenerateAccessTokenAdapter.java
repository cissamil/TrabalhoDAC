package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Usuario;
import br.ufpr.core.ports.output.GenerateAccessTokenOutputPort;
import br.ufpr.dataprovider.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateAccessTokenAdapter implements GenerateAccessTokenOutputPort {

  private final TokenService tokenService;

  @Override
  public String generate(Usuario usuario) {
    return tokenService.generateToken(usuario);
  }
}
