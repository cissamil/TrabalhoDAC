package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PendingContasDashboardOutputData {

  private GerenteOutputData gerenteOutputData;
  private List<PendingContaOutputData> contaOutputDataList;
  private List<PendingClienteOutputData> clienteOutputDataList;
}
