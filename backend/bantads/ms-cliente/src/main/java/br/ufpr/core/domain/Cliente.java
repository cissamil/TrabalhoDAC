package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

  private Integer id;
  private String clienteId;
  private String cpf;
  private String nome;
  private String email;
  private String telefone;
  private BigDecimal salario;
  private Endereco endereco;

}
