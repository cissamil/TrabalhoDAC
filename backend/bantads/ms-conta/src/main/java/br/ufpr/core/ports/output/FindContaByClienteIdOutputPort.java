package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

public interface FindContaByClienteIdOutputPort {

  Conta find(String clienteId);
}
