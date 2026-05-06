package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ClienteOutputData;

public interface ConsultClienteByClienteIdOutputPort {

  ClienteOutputData consult(String clienteId);
}
