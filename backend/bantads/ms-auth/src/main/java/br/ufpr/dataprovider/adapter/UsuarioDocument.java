package br.ufpr.dataprovider.adapter;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.lang.annotation.Documented;

@Data
@Document(collection = "usuarios")
public class UsuarioDocument {

  @Id
  private String id;
  @Indexed(unique = true)
  private String login; // E-mail (RF01)
  private String senha;
  private TipoUsuario tipoUsuario; // CLIENTE, GERENTE, ADMIN


}
