package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.core.domain.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "movimentacoes")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovimentacaoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "movimentacao_id")
  private String movimentacaoId;

  @Column(name = "data_hora")
  private Date dataHora;

  @Column(name = "cliente_origem_id")
  private String clienteOrigemId;

  @Column(name = "cliente_destino_id")
  private String clienteDestinoId;

  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_movimentacao")
  private TipoMovimentacao tipoMovimentacao;
}
