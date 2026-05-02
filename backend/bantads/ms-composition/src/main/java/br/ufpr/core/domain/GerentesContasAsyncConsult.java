package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerentesContasAsyncConsult {

  GerenteOutputData gerenteOutputData;
  List<PendingContaOutputData> contaOutputDataList;
}
