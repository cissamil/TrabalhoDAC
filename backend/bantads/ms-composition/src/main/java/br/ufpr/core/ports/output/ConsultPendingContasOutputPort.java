package br.ufpr.core.ports.output;

import br.ufpr.core.domain.PendingContaOutputData;
import br.ufpr.model.response.PendingContaResponse;

import java.util.List;

public interface ConsultPendingContasOutputPort {

  List<PendingContaOutputData> consult(String gerenteId);
}
