package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaWithMenorSaldoByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaWithMenorSaldoByGerenteIdAdapter implements FindContaWithMenorSaldoByGerenteIdOutputPort {

  private final ContaCommandRepository contaCommandRepository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String gerenteId) {

    ContaCommandEntity entity = contaCommandRepository.findContaWithMenorSaldoByGerenteId(gerenteId);

    return mapper.toDomain(entity);
  }
}
