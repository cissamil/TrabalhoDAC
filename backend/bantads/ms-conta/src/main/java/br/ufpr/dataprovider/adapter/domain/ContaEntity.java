package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.model.enumerator.StatusConta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "contas")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "numero_conta", unique = true)
  private String numeroConta;

  private BigDecimal saldo;
  private BigDecimal limite;

  @Column(name = "cliente_id")
  private String clienteId;

  @Column(name = "gerente_id")
  private String gerenteId;

  @Column(name = "data_criacao")
  private Date dataCriacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_conta")
  private StatusConta statusConta;
}
