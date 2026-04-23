package br.ufpr.core.ports.output;

import br.ufpr.core.domain.Cliente;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;

public interface SaveClienteOutputPort {

  Cliente save(Cliente cliente);


}
