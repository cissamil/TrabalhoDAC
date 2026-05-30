package br.ufpr.entrypoint.response;

import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.ContaResponse;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientesReportDashboardResponse {

  ClienteReportResponse cliente;
  ContaReportResponse conta;
  GerenteReportResponse gerente;
}
