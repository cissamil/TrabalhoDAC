package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.model.enumerator.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "pedidos")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PedidoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "cliente_id")
  private String clienteId;

  @Column(name = "gerente_id")
  private String gerenteId;

  @Column(name = "data_solicitacao")
  private Date dataSolicitacao;

  @Column(name = "data_decisao", nullable = true)
  private Date dataDecisao;

  @Column(name = "motivo_recusa", nullable = true)
  private String motivoRecusa;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pedido")
  private StatusPedido statusPedido;
}
