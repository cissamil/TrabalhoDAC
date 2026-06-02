package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

  private Long id;
  private String cep;
  private String logradouro;
  private Integer numero;
  private String cidade;
  private String estado;

}
