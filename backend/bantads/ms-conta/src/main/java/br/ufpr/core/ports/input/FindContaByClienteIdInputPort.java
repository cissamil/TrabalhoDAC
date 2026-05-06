package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Conta;

public interface FindContaByClienteIdInputPort {

  Conta find(String clienteId);
}
