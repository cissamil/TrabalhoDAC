package br.ufpr.dataprovider.adapter.domain;

import br.ufpr.model.enumerator.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UsuarioEntity {

  @Id
  private String id;
  @Indexed(unique = true)
  private String login; // E-mail (RF01)
  private String senha;
  private TipoUsuario tipoUsuario; // CLIENTE, GERENTE, ADMIN


}
