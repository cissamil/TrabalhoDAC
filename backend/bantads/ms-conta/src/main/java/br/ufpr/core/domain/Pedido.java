package br.ufpr.core.domain;

import br.ufpr.model.enumerator.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

  private Integer id;
  private String clienteId;
  private String gerenteId;
  private Date dataSolicitacao;
  private Date dataDecisao;
  private String motivoRecusa;
  private StatusPedido statusPedido;
}
