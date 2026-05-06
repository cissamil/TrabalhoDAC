package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaOutputData {

  private Integer id;
  private BigDecimal saldo;
  private BigDecimal limite;
  private String gerenteId;
  private String numeroConta;
}
