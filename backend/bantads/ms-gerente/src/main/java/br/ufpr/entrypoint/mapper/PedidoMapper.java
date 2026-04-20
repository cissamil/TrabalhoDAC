package br.ufpr.entrypoint.mapper;

import br.ufpr.dataprovider.adapter.PedidoAutocadastroEntity;
import br.ufpr.dataprovider.adapter.PedidoSagaDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

  public PedidoSagaDTO toDTO(PedidoAutocadastroEntity entity){
    final PedidoSagaDTO pedidoSagaDTO = new PedidoSagaDTO();

    pedidoSagaDTO.setSalario(entity.getSalario());
    pedidoSagaDTO.setCpfCliente(entity.getCpfCliente());
    pedidoSagaDTO.setCpfGerente(entity.getCpfGerente());
    pedidoSagaDTO.setNomeCliente(entity.getNomeCliente());
    pedidoSagaDTO.setNomeGerente(entity.getNomeGerente());
    pedidoSagaDTO.setStatusPedido(entity.getStatusPedido());
    pedidoSagaDTO.setEmailCliente(entity.getEmailCliente());


    return pedidoSagaDTO;
  }

}
