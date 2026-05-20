package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInputData {

  private String userId;
  private String email;
  private String senha;
  private TipoUsuario tipoUsuario;
}
