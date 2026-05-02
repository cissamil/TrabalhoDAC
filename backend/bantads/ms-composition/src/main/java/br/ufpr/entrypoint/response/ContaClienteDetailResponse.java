package br.ufpr.entrypoint.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaClienteDetailResponse {

  private Integer contaId;
  private String clienteId;
  private String clienteCpf;
  private String clienteNome;
  private String clienteEmail;
  private BigDecimal clienteSalario;

}
