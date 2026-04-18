package br.ufpr.entrypoint.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClienteRequest {
  @NotBlank
  @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
  private String cpf;

  @NotBlank
  private String nome;

  @Email(message = "E-mail inválido")
  @NotBlank
  private String email;

  @NotBlank
  private String telefone;

  @Null
  private String senha;

  @NotNull(message = "Salário é obrigatório") // @NotNull para BigDecimal
  @PositiveOrZero
  private BigDecimal salario;

  @NotBlank
  private String logradouro;

  @NotBlank
  @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
  private String cep;

  @NotBlank
  private String cidade;

  @NotBlank
  private String estado;
}
