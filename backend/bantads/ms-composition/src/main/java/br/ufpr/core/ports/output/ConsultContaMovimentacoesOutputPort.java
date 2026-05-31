package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.MovimentacaoOutputData;

import java.util.List;

public interface ConsultContaMovimentacoesOutputPort {

  List<MovimentacaoOutputData> consult(ConsultBankStatementInputData inputData);
}
