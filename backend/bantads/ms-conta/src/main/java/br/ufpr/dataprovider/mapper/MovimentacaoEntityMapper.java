package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Movimentacao;
import br.ufpr.dataprovider.adapter.domain.command.MovimentacaoCommandEntity;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoEntityMapper {

  public MovimentacaoCommandEntity toEntity(Movimentacao domain){

    if (domain == null) return null;

    MovimentacaoCommandEntity entity = new MovimentacaoCommandEntity();

    entity.setId(domain.getId());
    entity.setDataHora(domain.getDataHora());
    entity.setValor(domain.getValor());
    entity.setMovimentacaoId(domain.getMovimentacaoId());
    entity.setClienteOrigemId(domain.getClienteOrigemId());
    entity.setClienteDestinoId(domain.getClienteDestinoId());
    entity.setTipoMovimentacao(domain.getTipoMovimentacao());


    return entity;
  }


  public Movimentacao toDomain(MovimentacaoCommandEntity entity){

    Movimentacao domain = new Movimentacao();

    if (entity == null) return null;

    domain.setId(entity.getId());
    domain.setDataHora(entity.getDataHora());
    domain.setValor(entity.getValor());
    domain.setMovimentacaoId(entity.getMovimentacaoId());
    domain.setClienteOrigemId(entity.getClienteOrigemId());
    domain.setClienteDestinoId(entity.getClienteDestinoId());
    domain.setTipoMovimentacao(entity.getTipoMovimentacao());


    return domain;
  }
}
