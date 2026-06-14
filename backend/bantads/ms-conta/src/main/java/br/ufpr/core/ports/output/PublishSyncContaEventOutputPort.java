package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;

public interface PublishSyncContaEventOutputPort {

  void publish(Conta conta, Movimentacao movimentacao);
}
