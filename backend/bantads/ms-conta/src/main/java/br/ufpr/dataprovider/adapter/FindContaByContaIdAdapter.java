package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByContaIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByContaIdAdapter implements FindContaByContaIdOutputPort {

  private final ContaRepository contaRepository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String contaId) {

    ContaEntity entity = contaRepository.findByContaId(contaId);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String contaId) {
    return contaRepository.existsByContaId(contaId);
  }
}
