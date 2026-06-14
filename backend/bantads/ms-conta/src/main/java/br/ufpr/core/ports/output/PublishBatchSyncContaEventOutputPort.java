package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;

import java.util.List;

public interface PublishBatchSyncContaEventOutputPort {

  void publish(List<Conta> contas);
}
