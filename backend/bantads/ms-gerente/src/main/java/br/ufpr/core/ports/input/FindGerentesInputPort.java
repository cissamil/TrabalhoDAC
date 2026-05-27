package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Gerente;

import java.util.List;

public interface FindGerentesInputPort {

  List<Gerente> find();
}
