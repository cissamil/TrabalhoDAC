package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ClienteMovimentacoesDashboardOutputData;
import br.ufpr.core.domain.ConsultBankStatementInputData;

public interface GroupClienteMovimentacoesInputPort {

  ClienteMovimentacoesDashboardOutputData execute(ConsultBankStatementInputData inputData);
}
