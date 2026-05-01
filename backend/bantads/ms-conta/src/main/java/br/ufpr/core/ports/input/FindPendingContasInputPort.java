package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Conta;
import br.ufpr.model.response.PendingContaResponse;

import java.util.List;

public interface FindPendingContasInputPort {

  List<Conta> find(String gerenteId);
}
