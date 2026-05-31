package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.GroupGerenteStatisticsInputPort;
import br.ufpr.core.ports.output.ConsultApprovedContasOutputPort;
import br.ufpr.core.ports.output.ConsultGerentesOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
@RequiredArgsConstructor
public class GroupGerenteStatisticsUseCase implements GroupGerenteStatisticsInputPort {

  private final ConsultGerentesOutputPort consultGerentesOutputPort;
  private final ConsultApprovedContasOutputPort consultApprovedContasOutputPort;

  @Override
  public GerentesStatisticsDashboardOutputData execute() {

    GerentesContasAsyncConsult asyncConsult = gerenteAndContaOutputPortsMultipleAsyncConsult();

    GerentesStatisticsDashboardOutputData outputData = new GerentesStatisticsDashboardOutputData();

    outputData.setGerentes(asyncConsult.getGerentes());
    outputData.setContas(asyncConsult.getContas());

    return outputData;
  }


  private GerentesContasAsyncConsult gerenteAndContaOutputPortsMultipleAsyncConsult() {

    String emptyGerenteId = " ";

    CompletableFuture<List<GerenteOutputData>> gerenteFuture = CompletableFuture
      .supplyAsync(consultGerentesOutputPort::consult);

    CompletableFuture<List<ContaOutputData>> contaFuture = CompletableFuture
      .supplyAsync(() ->consultApprovedContasOutputPort.consult(emptyGerenteId));

    CompletableFuture.allOf(contaFuture, gerenteFuture).join();

    GerentesContasAsyncConsult asyncConsult = new GerentesContasAsyncConsult();

    asyncConsult.setGerentes(gerenteFuture.join());
    asyncConsult.setContas(contaFuture.join());

    return asyncConsult;
  }

}
