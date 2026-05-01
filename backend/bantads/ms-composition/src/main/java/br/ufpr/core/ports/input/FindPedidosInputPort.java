package br.ufpr.core.ports.input;

import br.ufpr.model.response.PedidosResponse;

public interface FindPedidosInputPort {

  PedidosResponse find(String gerenteId);
}
