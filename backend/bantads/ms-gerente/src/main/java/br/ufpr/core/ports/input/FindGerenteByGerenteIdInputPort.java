package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Gerente;

public interface FindGerenteByGerenteIdInputPort {

  Gerente find(String gerenteId);
}
