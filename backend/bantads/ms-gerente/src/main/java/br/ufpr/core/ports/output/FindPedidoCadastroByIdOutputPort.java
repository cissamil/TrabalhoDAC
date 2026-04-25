package br.ufpr.core.ports.output;

import br.ufpr.core.domain.PedidoCadastro;

public interface FindPedidoCadastroByIdOutputPort {

  PedidoCadastro find(Integer id);
  boolean exists(Integer id);
}
