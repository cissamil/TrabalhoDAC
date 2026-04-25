package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.ClienteRequestInputData;
import br.ufpr.core.domain.ClienteResponseOutputData;

public interface GetClienteInputPort {

  Cliente get(ClienteRequestInputData inputData);
}
