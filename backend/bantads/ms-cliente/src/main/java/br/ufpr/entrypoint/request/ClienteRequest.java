package br.ufpr.entrypoint.request;

import br.ufpr.core.domain.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteRequest {

//  @CPF
  @NotBlank
  @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
  private String cpf;

  @NotBlank
  private String nome;

  @Email(message = "E-mail inválido")
  @NotBlank
  private String email;

  @NotBlank
  @Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 dígitos")
  private String telefone;

  @NotNull(message = "Salário é obrigatório") // @NotNull para BigDecimal
  @PositiveOrZero
  private BigDecimal salario;

  @NotNull
  private EnderecoDTO endereco;
}
