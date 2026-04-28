package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.core.domain.TipoGerente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "gerentes")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GerenteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "gerente_id")
  private String gerenteId;

  // @CPF @TODO REMOVER ESSE COMENTÁRIO PARA QUE HAJA VALIDAÇÃO DE CPF QUANDO TERMINAR DE TESTAR
  @Column(unique = true, nullable = false)
  private String cpf;

  private String nome;
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_gerente")
  private TipoGerente tipoGerente;
}
