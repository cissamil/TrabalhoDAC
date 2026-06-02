package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.core.domain.TipoGerente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
@Table(name = "gerentes")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GerenteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "gerente_id", unique = true)
  private String gerenteId;

//  @CPF
  @Column(unique = true, nullable = false)
  private String cpf;

  private String nome;
  private String email;

  private String telefone;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_gerente")
  private TipoGerente tipoGerente;
}
