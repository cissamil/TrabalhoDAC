package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaByNumeroContaOutputPort {

  Conta find(Integer numeroConta);
  boolean exists(Integer numeroConta);
}
