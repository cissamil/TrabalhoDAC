package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Gerente;

import java.util.List;

public interface FindGerentesFromIdListInputPort {

  List<Gerente> find(List<String> gerenteIds);
}
