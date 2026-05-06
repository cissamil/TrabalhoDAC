package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ContaOutputData;

public interface ConsultContaByClienteIdOutputPort {

  ContaOutputData consult(String clienteId);
}
