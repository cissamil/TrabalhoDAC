package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteOutputData {

  private String nome;
  private String cpf;
  private String email;
  private String telefone;
  private String clienteId;
  private BigDecimal salario;
  private String endereco;

}
