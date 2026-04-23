package br.ufpr.core.ports.output;

import br.ufpr.model.message.TransferPedidoAutocadastroSagaMessage;

public interface SendStorePedidoAutocadastroMessageOutputPort {

  void send(TransferPedidoAutocadastroSagaMessage message);
}
