package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesGerentesAsyncConsult {

  private List<ClienteOutputData> clientes;
  private List<GerenteOutputData> gerentes;

}
