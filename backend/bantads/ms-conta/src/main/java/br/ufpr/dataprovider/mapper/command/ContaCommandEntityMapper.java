package br.ufpr.dataprovider.mapper.command;

import br.ufpr.core.domain.Conta;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import org.springframework.stereotype.Component;

@Component
public class ContaCommandEntityMapper {

  public Conta toDomain(ContaCommandEntity entity){

    if(entity == null) return null;

    Conta conta = new Conta();

    conta.setId(entity.getId());
    conta.setSaldo(entity.getSaldo());
    conta.setLimite(entity.getLimite());
    conta.setContaId(entity.getContaId());
    conta.setClienteId(entity.getClienteId());
    conta.setGerenteId(entity.getGerenteId());
    conta.setNumeroConta(entity.getNumeroConta());
    conta.setDataCriacao(entity.getDataCriacao());
    conta.setStatusConta(entity.getStatusConta());
    conta.setDataDecisao(entity.getDataDecisao());
    conta.setMotivoRecusa(entity.getMotivoRecusa());

    return conta;
  }

  public ContaCommandEntity toEntity(Conta conta){

    if(conta == null) return null;

    ContaCommandEntity entity = new ContaCommandEntity();

    entity.setId(conta.getId());
    entity.setSaldo(conta.getSaldo());
    entity.setLimite(conta.getLimite());
    entity.setContaId(conta.getContaId());
    entity.setClienteId(conta.getClienteId());
    entity.setGerenteId(conta.getGerenteId());
    entity.setNumeroConta(conta.getNumeroConta());
    entity.setDataCriacao(conta.getDataCriacao());
    entity.setStatusConta(conta.getStatusConta());
    entity.setDataDecisao(conta.getDataDecisao());
    entity.setMotivoRecusa(conta.getMotivoRecusa());

    return entity;
  }
}
