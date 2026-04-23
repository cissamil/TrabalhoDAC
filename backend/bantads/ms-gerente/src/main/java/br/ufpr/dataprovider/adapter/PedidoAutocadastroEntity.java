package br.ufpr.dataprovider.adapter;

import br.ufpr.model.message.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "pedidos_autocadastro")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PedidoAutocadastroEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String clienteId;
  private String gerenteId;
  private BigDecimal salario;

  @Enumerated(EnumType.STRING)
  private StatusPedido statusPedido;
  private String motivoReprovacao;
}
