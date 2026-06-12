package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaByContaIdOutputPort {

  Conta find(String contaId);
  boolean exists(String contaId);
}
