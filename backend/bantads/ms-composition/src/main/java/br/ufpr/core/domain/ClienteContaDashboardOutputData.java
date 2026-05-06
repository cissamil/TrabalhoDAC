package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteContaDashboardOutputData {

  ClienteOutputData clienteOutputData;
  ContaOutputData contaOutputData;
  GerenteOutputData gerenteOutputData;
}
