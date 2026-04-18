package br.ufpr.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

enum TipoMovimentacao{
  DEPOSITO,
  SAQUE,
  TRANSFERENCIA
}

@Data
@AllArgsConstructor
public class Movimentacao {
  private Integer id;
  private Date dataHora;
  private String clienteOrigem;
  private String clienteDestino;
  private String cpfClienteOrigem;
  private String cpfClienteDestino;
  private TipoMovimentacao tipoMovimentacao;
  private BigDecimal valor;
}
