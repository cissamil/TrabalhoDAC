package br.ufpr.dataprovider.adapter;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "contas")
public class ContaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private Integer numeroConta;

  private BigDecimal saldo;
  private BigDecimal limite;

  private String cpfGerente;
  private String nomeGerente;

  private String nomeCliente;
  private String cpfCliente;

  private Date dataCriacao;
}
