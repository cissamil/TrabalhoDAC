package br.ufpr.model.response;

import br.ufpr.model.enumerator.TipoGerente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GerenteResponse {
  private Integer id;
  private String gerenteId;
  private String cpf;
  private String nome;
  private String email;
  private TipoGerente tipoGerente;
}
