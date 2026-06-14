package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

import java.util.List;

public interface BatchSyncContaOutputPort {

  void sync(List<Conta> contas);
}
