package br.ufpr.entrypoint.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GerenteRequest {

//  @CPF
  @NotBlank
  @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
  private String cpf;

  @NotBlank
  private String nome;

  @NotBlank
  @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
  private String telefone;

  @Email(message = "E-mail inválido")
  @NotBlank
  private String email;

  @NotBlank
  private String senha;
}
