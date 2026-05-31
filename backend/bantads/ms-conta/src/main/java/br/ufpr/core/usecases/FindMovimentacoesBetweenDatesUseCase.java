package br.ufpr.core.usecases;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.input.FindMovimentacoesBetweenDatesInputPort;
import br.ufpr.core.ports.output.FindMovimentacoesBetweenDatesOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindMovimentacoesBetweenDatesUseCase implements FindMovimentacoesBetweenDatesInputPort {

  private final FindMovimentacoesBetweenDatesOutputPort findMovimentacoesBetweenDatesOutputPort;

  @Override
  public List<Movimentacao> find(ConsultBankStatementInputData inputData) {
    return findMovimentacoesBetweenDatesOutputPort.find(inputData);
  }
}
