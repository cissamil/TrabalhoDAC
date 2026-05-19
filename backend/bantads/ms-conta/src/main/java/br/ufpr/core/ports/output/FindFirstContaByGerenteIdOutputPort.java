package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindFirstContaByGerenteIdOutputPort {

  Conta find(String gerenteId);
}
