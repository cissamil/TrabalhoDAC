package br.ufpr.models;

import java.math.BigDecimal;
import java.util.Date;


public class PedidoAutoCadastro {
  private Integer id;
  private Date dataSolicitacao;
  private StatusPedido statusPedido;
  private Date datDecisao;
  private String motivoRecusa;
  private Conta contaGerada;
  private String nomeCliente;
  private String nomeGerente;
  private String cpfClienteOrigem;
  private String cpfGerente;
  private String emailCliente;
  private TipoMovimentacao tipoMovimentacao;
  private BigDecimal salario;
}
