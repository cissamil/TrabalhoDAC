package br.ufpr.dataprovider.adapter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "clientes")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClienteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "cliente_id")
  private String clienteId;

  // @CPF @TODO REMOVER ESSE COMENTÁRIO PARA QUE HAJA VALIDAÇÃO DE CPF QUANDO TERMINAR DE TESTAR
  @Column(unique = true, nullable = false)
  private String cpf;

  private String nome;
  private String email;
  private String telefone;
  private BigDecimal salario;
  private String endereco;

}
