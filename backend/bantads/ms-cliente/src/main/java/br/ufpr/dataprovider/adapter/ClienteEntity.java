package br.ufpr.dataprovider.adapter;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "clientes")
public class ClienteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String cpf;

  private String nome;
  private String email;
  private String telefone;
  private String senha;
  private BigDecimal salario;
  private String endereco;

}
