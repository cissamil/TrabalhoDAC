package br.ufpr.core.usecases;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.core.domain.TransferPedidoCadastroInputData;
import br.ufpr.core.mapper.PedidoCadastroMapper;
import br.ufpr.core.ports.input.SavePedidoCadastroInputPort;
import br.ufpr.core.ports.output.SavePedidoCadastroOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavePedidoCadastroUseCase implements SavePedidoCadastroInputPort {

  private final PedidoCadastroMapper mapper;
  private final SavePedidoCadastroOutputPort savePedidoCadastroOutputPort;

  public void save(TransferPedidoCadastroInputData inputData){

    PedidoCadastro pedidoCadastro = mapper.toDomain(inputData);

    savePedidoCadastroOutputPort.save(pedidoCadastro);
  }
}
