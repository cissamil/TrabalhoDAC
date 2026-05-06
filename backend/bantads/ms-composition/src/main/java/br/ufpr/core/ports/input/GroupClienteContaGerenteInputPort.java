package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ClienteContaDashboardOutputData;

public interface GroupClienteContaGerenteInputPort {

  ClienteContaDashboardOutputData execute(String clienteId);
}
