package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.PedidoCadastro;
import br.ufpr.core.ports.output.FindPedidoCadastroByIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.PedidoAutocadastroEntity;
import br.ufpr.dataprovider.client.PedidoAutocadastroRepository;
import br.ufpr.dataprovider.mapper.PedidoAutocadastroEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindPedidoCadastroByIdAdapter implements FindPedidoCadastroByIdOutputPort {

  private final PedidoAutocadastroRepository repository;
  private final PedidoAutocadastroEntityMapper mapper;

  @Override
  public PedidoCadastro find(Integer id) {

     PedidoAutocadastroEntity entity = repository.findById(id).orElse(null);

     return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(Integer id) {
    return repository.existsById(id);
  }
}
