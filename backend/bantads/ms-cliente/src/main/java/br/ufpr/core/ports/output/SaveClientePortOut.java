package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;

public interface SaveClientePortOut {

  void save(Cliente cliente);
  boolean existsByCpf(String cpf);
}
