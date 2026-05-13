package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Movimentacao;

public interface RegisterNewMovimentacaoOutputPort {

  Movimentacao register(Movimentacao movimentacao);
}
