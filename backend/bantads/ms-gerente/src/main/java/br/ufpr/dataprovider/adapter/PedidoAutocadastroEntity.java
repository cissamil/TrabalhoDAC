package br.ufpr.dataprovider.adapter;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "pedidos_autocadastro")
@Data
public class PedidoAutocadastroEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cpfCliente;
  private String nomeCliente;
  private BigDecimal salario;
  private String nomeGerente;
  private String cpfGerente;
  private String emailCliente;

  @Enumerated(EnumType.STRING)
  private StatusPedido statusPedido;
  private String motivoReprovacao;
}
