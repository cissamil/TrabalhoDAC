package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

public interface SaveGerenteOutputPort {

  Gerente save(Gerente gerente);
}
