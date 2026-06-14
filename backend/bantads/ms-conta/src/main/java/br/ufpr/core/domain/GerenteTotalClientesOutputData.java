package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerenteTotalClientesOutputData {

  private String gerente;
  private int qtdClientes;
  private BigDecimal menorSaldoPositivo;

}
