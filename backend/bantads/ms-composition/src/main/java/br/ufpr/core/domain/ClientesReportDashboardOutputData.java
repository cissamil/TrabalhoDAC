package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesReportDashboardOutputData {

  private List<ClienteOutputData> clientes;
  private List<ContaOutputData> contas;
  private List<GerenteOutputData> gerentes;
}
