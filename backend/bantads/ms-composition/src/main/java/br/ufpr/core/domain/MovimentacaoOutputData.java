package br.ufpr.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovimentacaoOutputData {

  private Date dataHora;
  private BigDecimal valor;
  private String movimentacaoId;
  private String clienteOrigemId;
  private String clienteDestinoId;
  private String clienteOrigemNome;
  private String clienteDestinoNome;
  private TipoMovimentacao tipoMovimentacao;
}
