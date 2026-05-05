package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ClienteOutputData;

public interface ConsultClienteOutputPort {

  ClienteOutputData consult(String clienteId);
}
