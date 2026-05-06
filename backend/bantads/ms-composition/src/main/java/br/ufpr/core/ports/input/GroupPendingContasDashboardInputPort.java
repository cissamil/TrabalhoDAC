package br.ufpr.core.ports.input;

import br.ufpr.core.domain.PendingContasDashboardOutputData;

public interface GroupPendingContasDashboardInputPort {

  PendingContasDashboardOutputData execute(String gerenteId);
}
