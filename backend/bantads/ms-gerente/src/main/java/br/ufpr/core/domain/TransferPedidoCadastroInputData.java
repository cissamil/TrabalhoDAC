package br.ufpr.core.domain;

import br.ufpr.model.message.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferPedidoCadastroInputData {
  private String clienteId;
  private String gerenteId;
  private BigDecimal salario;
  private StatusPedido statusPedido;
}
