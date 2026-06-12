package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveContaAdapter implements SaveContaOutputPort {

  private final ContaEntityMapper mapper;
  private final ContaCommandRepository repository;

  public Conta save(Conta conta){

    try {
      ContaCommandEntity entity = mapper.toEntity(conta);

      ContaCommandEntity savedEntity = repository.save(entity);

      return mapper.toDomain(savedEntity);

    } catch (Exception e) {
      throw new RuntimeException("Erro ao salvar cliente. Tente novamente");
    }

  }
}
