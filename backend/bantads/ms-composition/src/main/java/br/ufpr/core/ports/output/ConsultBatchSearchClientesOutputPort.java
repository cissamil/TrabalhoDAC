package br.ufpr.core.ports.output;

import br.ufpr.core.domain.PendingClienteOutputData;

import java.util.List;

public interface ConsultBatchSearchClientesOutputPort {

  List<PendingClienteOutputData> consult(List<String> clienteIds);
}
