package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaByNumeroContaOutputPort {

  Conta find(String numeroConta);
  boolean exists(Integer numeroConta);
}
