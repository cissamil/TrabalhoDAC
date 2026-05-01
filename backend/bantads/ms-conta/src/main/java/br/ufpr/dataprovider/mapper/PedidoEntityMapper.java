package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Pedido;
import br.ufpr.dataprovider.adapter.domain.PedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoEntityMapper {

  public Pedido toDomain(PedidoEntity entity){

    if(entity == null) return null;

    Pedido pedido = new Pedido();

    pedido.setId(entity.getId());
    pedido.setClienteId(entity.getClienteId());
    pedido.setGerenteId(entity.getGerenteId());
    pedido.setDataSolicitacao(entity.getDataSolicitacao());
    pedido.setDataDecisao(entity.getDataDecisao());
    pedido.setMotivoRecusa(entity.getMotivoRecusa());
    pedido.setStatusPedido(entity.getStatusPedido());

    return pedido;
  }

  public PedidoEntity toEntity(Pedido pedido){

    if(pedido == null) return null;

    PedidoEntity entity = new PedidoEntity();

    entity.setId(pedido.getId());
    entity.setClienteId(pedido.getClienteId());
    entity.setGerenteId(pedido.getGerenteId());
    entity.setDataSolicitacao(pedido.getDataSolicitacao());
    entity.setDataDecisao(pedido.getDataDecisao());
    entity.setMotivoRecusa(pedido.getMotivoRecusa());
    entity.setStatusPedido(pedido.getStatusPedido());

    return entity;
  }
}
