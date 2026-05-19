package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;

import java.util.List;

public interface FindContasByGerenteIdOutputPort {

  List<Conta> find(String gerenteId);
}
