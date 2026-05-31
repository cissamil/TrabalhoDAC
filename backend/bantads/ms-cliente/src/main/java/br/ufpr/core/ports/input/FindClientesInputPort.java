package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Cliente;

import java.util.List;

public interface FindClientesInputPort {

  List<Cliente> find();
}
