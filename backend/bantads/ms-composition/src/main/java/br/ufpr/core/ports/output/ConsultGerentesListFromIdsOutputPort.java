package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.GerenteOutputData;

import java.util.List;

public interface ConsultGerentesListFromIdsOutputPort {

  List<GerenteOutputData> consult(List<String> gerentesIds);
}
