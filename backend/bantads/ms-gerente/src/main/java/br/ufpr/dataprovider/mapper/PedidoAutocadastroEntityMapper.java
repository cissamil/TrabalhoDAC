package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.dataprovider.adapter.PedidoAutocadastroEntity;

public class PedidoAutocadastroEntityMapper {

  public PedidoCadastro toDomain(PedidoAutocadastroEntity entity){
    if(entity == null) return null;

    PedidoCadastro pedidoCadastro = new PedidoCadastro();

    pedidoCadastro.setId(entity.getId());
    pedidoCadastro.setClienteId(entity.getClienteId());
    pedidoCadastro.setGerenteId(entity.getGerenteId());
    pedidoCadastro.setSalario(entity.getSalario());
    pedidoCadastro.setStatusPedido(entity.getStatusPedido());

    return pedidoCadastro;
  }

  public PedidoAutocadastroEntity toEntity(PedidoCadastro pedidoCadastro){
    if(pedidoCadastro == null) return null;

    PedidoAutocadastroEntity entity = new PedidoAutocadastroEntity();

    entity.setId(pedidoCadastro.getId());
    entity.setClienteId(pedidoCadastro.getClienteId());
    entity.setGerenteId(pedidoCadastro.getGerenteId());
    entity.setSalario(pedidoCadastro.getSalario());
    entity.setStatusPedido(pedidoCadastro.getStatusPedido());

    return entity;
  }
}
