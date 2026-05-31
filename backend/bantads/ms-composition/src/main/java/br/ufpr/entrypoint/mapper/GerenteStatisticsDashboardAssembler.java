package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.domain.GerentesStatisticsDashboardOutputData;
import br.ufpr.entrypoint.response.GerenteStatisticsDashboardResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Component
public class GerenteStatisticsDashboardAssembler {

  private enum BalanceSignal{
    POSITIVE,
    NEGATIVE

  }

  public List<GerenteStatisticsDashboardResponse> assemble(GerentesStatisticsDashboardOutputData outputData){

    List<GerenteOutputData> gerentes = outputData.getGerentes();
    List<ContaOutputData> contas = outputData.getContas();

    return gerentes.stream().map(gerente ->{

      List<ContaOutputData> contasByGerente = contas
        .stream()
        .filter(conta -> Objects.equals(conta.getGerenteId(), gerente.getGerenteId()))
        .toList();

      if(contasByGerente.isEmpty()) return null;

      int clientesQuantity = contasByGerente.size();

      BigDecimal positiveBalanceSum = getBalanceSum(contasByGerente, BalanceSignal.POSITIVE);

      BigDecimal negativeBalanceSums = getBalanceSum(contasByGerente, BalanceSignal.NEGATIVE);

      return convertToResponseObject(gerente, clientesQuantity, positiveBalanceSum, negativeBalanceSums);

    })
      .filter(Objects::nonNull)
      .sorted(Comparator.comparing(GerenteStatisticsDashboardResponse::getSomaSaldosPositivos).reversed())
      .toList();
  }
  private BigDecimal getBalanceSum(List<ContaOutputData> contasByGerente, BalanceSignal signal){

    List<BigDecimal> balances = contasByGerente
      .stream()
      .map(ContaOutputData::getSaldo)
      .toList();

    return switch (signal) {
      case POSITIVE -> balances.stream()
        .filter(saldo -> saldo.compareTo(BigDecimal.ZERO) >= 0)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
      case NEGATIVE -> balances
        .stream()
        .filter(saldo -> saldo.compareTo(BigDecimal.ZERO) < 0)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    };

  }

  private GerenteStatisticsDashboardResponse convertToResponseObject(
    GerenteOutputData gerente,
    int clientesQuantity,
    BigDecimal positiveBalanceSum,
    BigDecimal negativeBalanceSums
  ){
    GerenteStatisticsDashboardResponse response = new GerenteStatisticsDashboardResponse();

    response.setGerenteId(gerente.getGerenteId());
    response.setGerenteNome(gerente.getNome());
    response.setGerenteCPF(gerente.getCpf());
    response.setQuantidadeClientes(clientesQuantity);
    response.setSomaSaldosPositivos(positiveBalanceSum);
    response.setSomaSaldosNegativos(negativeBalanceSums);
    return response;
  }

}


