package br.ufpr.core.ports.output;

import br.ufpr.core.domain.PedidoCadastro;

public interface SavePedidoCadastroOutputPort {

  PedidoCadastro save(PedidoCadastro pedidoCadastro);
}
