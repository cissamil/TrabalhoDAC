package br.ufpr.entrypoint.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteMovimentacoesDashboardResponse {

  private String clienteId;
  List<MovimentacaoEntrypointResponse> movimentacoes;
}
