package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface SaveContaOutputPort {

  Conta save(Conta conta);
}
