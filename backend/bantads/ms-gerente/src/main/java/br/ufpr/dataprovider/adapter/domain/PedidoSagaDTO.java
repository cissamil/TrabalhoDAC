package br.ufpr.dataprovider.adapter.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoSagaDTO {

  private String cpfCliente;
  private String cpfGerente;
  private BigDecimal salario;
  private String nomeGerente;
  private String nomeCliente;
  private String emailCliente;
  private StatusPedido statusPedido;

}
