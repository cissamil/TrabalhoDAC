package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GerenteInputData {
  private String nome;
  private String email;
  private String cpf;
  private String senha;
  private String telefone;
}
