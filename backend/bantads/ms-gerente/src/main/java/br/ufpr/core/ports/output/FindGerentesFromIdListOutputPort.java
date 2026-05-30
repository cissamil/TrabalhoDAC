package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

import java.util.List;

public interface FindGerentesFromIdListOutputPort {

  List<Gerente> find(List<String> gerenteIds);
}
