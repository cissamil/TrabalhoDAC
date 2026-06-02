package br.ufpr.dataprovider.adapter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "enderecos")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EnderecoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String logradouro;
  private Integer numero;
  private String cep;
  private String cidade;
  private String estado;

  @OneToOne(mappedBy = "endereco")
  private ClienteEntity cliente;
}
