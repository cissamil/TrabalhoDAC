package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ClientesContasDashboardOutputData;

public interface GroupClientesContasInputPort {

  ClientesContasDashboardOutputData execute(String gerenteId);
}
