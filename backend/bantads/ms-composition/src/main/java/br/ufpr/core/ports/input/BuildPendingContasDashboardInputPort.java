package br.ufpr.core.ports.input;

import br.ufpr.core.domain.PendingContasDashboardOutputData;
import br.ufpr.entrypoint.response.PendingContasDashboardResponse;

public interface BuildPendingContasDashboardInputPort {

  PendingContasDashboardOutputData execute(String gerenteId);
}
