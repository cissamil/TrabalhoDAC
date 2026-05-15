package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferValueInputData {
  private String originContaNumber;
  private String destinyContaNumber;
  private BigDecimal value;
}
