package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerentesContasAsyncConsult {

  List<GerenteOutputData> gerentes;
  List<ContaOutputData> contas;
}
