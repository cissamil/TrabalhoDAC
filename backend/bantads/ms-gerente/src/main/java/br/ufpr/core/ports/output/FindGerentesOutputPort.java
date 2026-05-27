package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Gerente;

import java.util.List;

public interface FindGerentesOutputPort {

  List<Gerente> find();
}
