package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class Conta {
  private Integer id;
  private BigDecimal saldo;
  private BigDecimal limite;
  private String gerente;
  private String cliente;
  private Date dataCriacao;
  private String cpfGerente;
  private String cpfCliente;
  private Integer numeroConta;
}
