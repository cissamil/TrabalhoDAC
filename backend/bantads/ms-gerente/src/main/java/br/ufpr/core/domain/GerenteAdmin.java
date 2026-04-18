package br.ufpr.core.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

enum TipoGerente{
  GERENTE,
  ADMIN
}

@Entity
@Table(name = "GerenteAdmin")
@Data
@AllArgsConstructor
public class GerenteAdmin {
  private Integer id;
  private String cpf;
  private String nome;
  private String email;
  private String telefone;
  private String senha;
  private TipoGerente tipoGerente;

}
