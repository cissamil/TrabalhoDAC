package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ClientesContasDashboardOutputData;
import br.ufpr.core.domain.ClientesReportDashboardOutputData;

public interface GroupClientesConsultInputPort {

  ClientesContasDashboardOutputData execute(String gerenteId);
}
