package br.ufpr.core.ports.output;

import br.ufpr.core.domain.LargestBalancesContasOutputData;

import java.util.List;

public interface ConsultLargestBalanceContasOutputPort{

  List<LargestBalancesContasOutputData> consult(int quantity);
}
