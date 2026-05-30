package br.ufpr.core.ports.output;

import java.math.BigDecimal;

public interface PublishUpdateContaLimitEventOutputPort {

  void publish(String clienteId, BigDecimal clienteSalary);
}
