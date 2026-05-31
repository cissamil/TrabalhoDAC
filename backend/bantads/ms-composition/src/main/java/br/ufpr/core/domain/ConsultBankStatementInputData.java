package br.ufpr.core.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultBankStatementInputData {

  private String clienteId;
  private LocalDate dataInicio;
  private LocalDate dataFim;
}
