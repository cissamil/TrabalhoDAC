package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
  private Integer id;
  private String contaId;
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
