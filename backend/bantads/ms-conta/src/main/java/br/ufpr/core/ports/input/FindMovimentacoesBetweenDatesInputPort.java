package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.Movimentacao;

import java.util.List;

public interface FindMovimentacoesBetweenDatesInputPort {

  List<Movimentacao> find(ConsultBankStatementInputData inputData);

}
