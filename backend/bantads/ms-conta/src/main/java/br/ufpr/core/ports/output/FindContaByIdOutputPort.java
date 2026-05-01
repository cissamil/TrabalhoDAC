package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaByIdOutputPort {

  Conta find(Integer contaId);
}
