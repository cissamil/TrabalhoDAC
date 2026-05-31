package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ClienteOutputData;

public interface ConsultClienteEmailOutputPort {

  ClienteOutputData consult(String clienteId);
}
