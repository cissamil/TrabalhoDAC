package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferContaCreationDataInputData {

  private String clienteId;
  private String gerenteId;
  private BigDecimal salario;
}
