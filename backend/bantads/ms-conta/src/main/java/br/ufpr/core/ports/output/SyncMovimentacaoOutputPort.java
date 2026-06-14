package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Movimentacao;

public interface SyncMovimentacaoOutputPort {

  void sync(Movimentacao movimentacao);
}
