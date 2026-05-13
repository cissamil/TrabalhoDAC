package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

  private Integer id;
  private Date dataHora;
  private BigDecimal valor;
  private String movimentacaoId;
  private String clienteOrigemId;
  private String clienteDestinoId;
  private TipoMovimentacao tipoMovimentacao;
}
