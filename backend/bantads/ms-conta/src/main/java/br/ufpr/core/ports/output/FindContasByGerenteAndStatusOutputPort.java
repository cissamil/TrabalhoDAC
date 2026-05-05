package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.StatusConta;

import java.util.List;

public interface FindContasByGerenteAndStatusOutputPort {

  List<Conta> find(String gerenteId, StatusConta statusConta);
}
