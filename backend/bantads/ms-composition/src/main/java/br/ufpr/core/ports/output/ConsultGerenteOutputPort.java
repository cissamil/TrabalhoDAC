package br.ufpr.core.ports.output;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.dataprovider.client.domain.GerenteResponse;

public interface ConsultGerenteOutputPort {

  GerenteOutputData consult(String gerenteId);
}
