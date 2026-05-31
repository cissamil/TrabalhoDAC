package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.FindMovimentacoesBetweenDatesOutputPort;
import br.ufpr.dataprovider.adapter.domain.MovimentacaoEntity;
import br.ufpr.dataprovider.client.MovimentacaoRepository;
import br.ufpr.dataprovider.mapper.MovimentacaoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindMovimentacoesBetweenDatesAdapter implements FindMovimentacoesBetweenDatesOutputPort {

  private final MovimentacaoRepository repository;
  private final MovimentacaoEntityMapper mapper;
  @Override
  public List<Movimentacao> find(ConsultBankStatementInputData inputData) {

    LocalDate dataInicio = inputData.getDataInicio();
    LocalDate dataFim = inputData.getDataFim();
    String clienteId = inputData.getClienteId();

    LocalDateTime dataInicioTime = dataInicio.atStartOfDay();
    LocalDateTime dataFimTime = dataFim.plusDays(1).atStartOfDay().minusMinutes(1);

    System.out.println("Pegando movimentações do dia " + dataInicioTime + " até o dia " + dataFimTime);

    List<MovimentacaoEntity> movimentacaoEntities = repository.findMovimentacoesByClienteIdBetweenDates(dataInicioTime, dataFimTime, clienteId);

    return movimentacaoEntities.stream().map(mapper::toDomain).toList();
  }
}
