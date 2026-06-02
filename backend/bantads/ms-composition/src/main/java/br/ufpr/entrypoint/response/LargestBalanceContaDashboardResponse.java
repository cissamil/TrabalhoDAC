package br.ufpr.entrypoint.response;

import br.ufpr.core.domain.EnderecoOutputData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LargestBalanceContaDashboardResponse {

  private String nome;
  private String cpf;
  private String clienteId;
  private String contaId;
  private BigDecimal saldo;
  private EnderecoOutputData endereco;
}
