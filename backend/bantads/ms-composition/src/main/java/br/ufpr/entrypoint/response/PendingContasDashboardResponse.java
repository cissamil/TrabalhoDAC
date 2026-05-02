package br.ufpr.entrypoint.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingContasDashboardResponse {

  private GerenteSummaryResponse gerente;
  private List<ContaClienteDetailResponse> contas;
}
