package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Conta;

import java.util.List;

public interface FindContasOrderedBySaldoBasedOnQuantityOutputPort {

  List<Conta> find(int quantity);
}
