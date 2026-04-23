package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Conta;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;

public class ContaEntityMapper {

  public Conta toDomain(ContaEntity entity){

    if(entity == null) return null;

    Conta conta = new Conta();

    conta.setId(entity.getId());
    conta.setNumeroConta(entity.getNumeroConta());
    conta.setSaldo(entity.getSaldo());
    conta.setLimite(entity.getLimite());
    conta.setClienteId(entity.getClienteId());
    conta.setGerenteId(entity.getGerenteId());
    conta.setDataCriacao(entity.getDataCriacao());

    return conta;
  }

  public ContaEntity toEntity(Conta conta){

    if(conta == null) return null;

    ContaEntity entity = new ContaEntity();

    entity.setNumeroConta(conta.getNumeroConta());
    entity.setId(conta.getId());
    entity.setSaldo(conta.getSaldo());
    entity.setLimite(conta.getLimite());
    entity.setClienteId(conta.getClienteId());
    entity.setGerenteId(conta.getGerenteId());
    entity.setDataCriacao(conta.getDataCriacao());

    return entity;
  }
}
