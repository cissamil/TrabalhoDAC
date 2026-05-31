package br.ufpr.entrypoint.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GerenteStatisticsDashboardResponse {

  private String gerenteId;
  private String gerenteNome;
  private String gerenteCPF;
  private int quantidadeClientes;
  private BigDecimal somaSaldosPositivos;
  private BigDecimal somaSaldosNegativos;
}
