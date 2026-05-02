package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

public interface FindGerenteByGerenteIdOutputPort {

  Gerente find(String gerenteId);
}
