package br.ufpr.core.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GerenteAdmin {
  private Integer id;
  private String cpf;
  private String nome;
  private String email;
  private String telefone;
  private String senha;
  private TipoGerente tipoGerente;

}
