package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface SyncContaOutputPort {

  void sync(Conta conta);
}
