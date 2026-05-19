package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

import java.util.List;

public interface SaveContasOutputPort {

  List<Conta> save(List<Conta> contas);
}
