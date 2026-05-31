package br.ufpr.core.ports.output;

import br.ufpr.core.domain.GerenteOutputData;

import java.util.List;

public interface ConsultGerentesOutputPort {

  List<GerenteOutputData> consult();
}
