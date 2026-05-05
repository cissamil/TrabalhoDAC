package br.ufpr.entrypoint.response;

import br.ufpr.core.domain.StatusConta;
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
public class ContaResponse {

  private Integer id;
  private BigDecimal saldo;
  private BigDecimal limite;
  private String clienteId;
  private String gerenteId;
  private Date dataCriacao;
  private String numeroConta;
  private StatusConta statusConta;
  private Date dataDecisao;
  private String motivoRecusa;
}
