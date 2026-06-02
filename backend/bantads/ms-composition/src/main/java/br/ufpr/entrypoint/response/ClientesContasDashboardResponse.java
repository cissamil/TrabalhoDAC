package br.ufpr.entrypoint.response;

import br.ufpr.core.domain.EnderecoOutputData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientesContasDashboardResponse {

  private String nome;
  private String cpf;
  private String clienteId;
  private BigDecimal saldo;
  private String contaId;
  private BigDecimal limite;
  private EnderecoOutputData endereco;
}
