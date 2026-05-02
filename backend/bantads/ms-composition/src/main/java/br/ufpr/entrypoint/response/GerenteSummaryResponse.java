package br.ufpr.entrypoint.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerenteSummaryResponse {

  private String nome;
  private String gerenteId;
}
