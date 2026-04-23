package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.core.ports.output.SavePedidoCadastroOutputPort;
import br.ufpr.dataprovider.client.PedidoAutocadastroRepository;
import br.ufpr.dataprovider.mapper.PedidoAutocadastroEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavePedidoCadastroAdapter implements SavePedidoCadastroOutputPort {

  private PedidoAutocadastroRepository repository;
  private PedidoAutocadastroEntityMapper mapper;

  public PedidoCadastro save(PedidoCadastro pedidoCadastro){

    PedidoAutocadastroEntity entity = mapper.toEntity(pedidoCadastro);

    PedidoAutocadastroEntity savedEntity = repository.save(entity);

    return mapper.toDomain(savedEntity);

  }


}
