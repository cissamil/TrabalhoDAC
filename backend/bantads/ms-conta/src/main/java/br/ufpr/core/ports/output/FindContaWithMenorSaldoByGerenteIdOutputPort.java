package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaWithMenorSaldoByGerenteIdOutputPort {

  Conta find(String gerenteId);
}
