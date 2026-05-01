package br.ufpr.model.response;

import br.ufpr.model.enumerator.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PendingPedidoResponse {
  private Integer id;
  private String clienteId;
  private String gerenteId;
  private Date dataSolicitacao;
  private Date dataDecisao;
  private String motivoRecusa;
  private StatusPedido statusPedido;
}
