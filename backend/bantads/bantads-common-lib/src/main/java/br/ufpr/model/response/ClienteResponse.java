package br.ufpr.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClienteResponse {

  private Integer id;
  private String clienteId;
  private String cpf;
  private String nome;
  private String email;
  private String telefone;
  private BigDecimal salario;
  private String endereco;

}
