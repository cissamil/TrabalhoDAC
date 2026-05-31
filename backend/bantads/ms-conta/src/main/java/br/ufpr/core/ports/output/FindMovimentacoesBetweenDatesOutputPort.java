package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.Movimentacao;

import java.util.List;

public interface FindMovimentacoesBetweenDatesOutputPort {

  List<Movimentacao> find(ConsultBankStatementInputData inputData);
}
