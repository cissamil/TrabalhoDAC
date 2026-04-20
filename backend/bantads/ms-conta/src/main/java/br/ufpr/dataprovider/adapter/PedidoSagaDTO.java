package br.ufpr.dataprovider.adapter;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoSagaDTO {

  private String cpfCliente;
  private String nomeCliente;
  private String emailCliente;
  private BigDecimal salario;
  private StatusPedido statusPedido;
  private String cpfGerente;
  private String nomeGerente;

}
