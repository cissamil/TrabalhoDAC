package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

import java.util.List;

public interface FindClientesOutputPort {

  List<Cliente> find();
}
