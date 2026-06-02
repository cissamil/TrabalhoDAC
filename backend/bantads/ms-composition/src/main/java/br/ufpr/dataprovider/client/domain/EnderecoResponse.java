package br.ufpr.dataprovider.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoResponse {

  private Long id;

  @NotNull @NotBlank
  @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
  private String cep;

  @NotNull @NotBlank
  private Integer numero;

  @NotNull @NotBlank
  private String cidade;

  @NotNull @NotBlank
  private String estado;

  @NotNull @NotBlank
  private String logradouro;
}
