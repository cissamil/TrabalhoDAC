package br.ufpr.core.mapper;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.core.domain.TransferPedidoCadastroInputData;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastroMapper {

  public PedidoCadastro toDomain(TransferPedidoCadastroInputData inputData){

    if(inputData == null){
      return null;
    }

    PedidoCadastro pedidoCadastro = new PedidoCadastro();

    pedidoCadastro.setSalario(inputData.getSalario());
    pedidoCadastro.setGerenteId(inputData.getGerenteId());
    pedidoCadastro.setClienteId(inputData.getClienteId());
    pedidoCadastro.setStatusPedido(inputData.getStatusPedido());

    return pedidoCadastro;
  }
}
