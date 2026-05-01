package br.ufpr.model.response;

import br.ufpr.model.enumerator.StatusPedido;

import java.math.BigDecimal;
import java.util.Date;

public class ClientesPedidosResponse {

  private Integer id;
  private String clienteId;
  private String gerenteId;
  private Date dataSolicitacao;
  private Date dataDecisao;
  private String motivoRecusa;
  private StatusPedido statusPedido;
  private String nome;
  private String email;
  private BigDecimal salario;
}
