package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaWithMenorSaldoByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaWithMenorSaldoByGerenteIdAdapter implements FindContaWithMenorSaldoByGerenteIdOutputPort {

  private final ContaRepository contaRepository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String gerenteId) {

    ContaEntity entity = contaRepository.findContaWithMenorSaldoByGerenteId(gerenteId);

    return mapper.toDomain(entity);
  }
}
