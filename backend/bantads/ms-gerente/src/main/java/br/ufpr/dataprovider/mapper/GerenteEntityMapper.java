package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Gerente;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.entrypoint.response.GerenteResponse;
import org.springframework.stereotype.Component;

@Component
public class GerenteEntityMapper {

  public Gerente toDomain(GerenteEntity entity){

    if (entity == null) return null;

    Gerente gerente = new Gerente();

    gerente.setId(entity.getId());
    gerente.setCpf(entity.getCpf());
    gerente.setNome(entity.getNome());
    gerente.setEmail(entity.getEmail());
    gerente.setGerenteId(entity.getGerenteId());
    gerente.setTipoGerente(entity.getTipoGerente());

    return gerente;
  }

  public GerenteEntity toEntity(Gerente gerente){

    if (gerente == null) return null;

    GerenteEntity entity = new GerenteEntity();

    entity.setId(gerente.getId());
    entity.setCpf(gerente.getCpf());
    entity.setNome(gerente.getNome());
    entity.setEmail(gerente.getEmail());
    entity.setGerenteId(gerente.getGerenteId());
    entity.setTipoGerente(gerente.getTipoGerente());

    return entity;
  }
}
