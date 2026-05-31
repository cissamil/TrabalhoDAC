package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesContasDashboardOutputData {

  List<ClienteOutputData> clientes;
  List<ContaOutputData> contas;
}
