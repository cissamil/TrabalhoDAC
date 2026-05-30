package br.ufpr.core.ports.output;

import br.ufpr.core.domain.ContaOutputData;

import java.util.List;

public interface ConsultApprovedContasOutputPort {

  List<ContaOutputData> consult();
}
