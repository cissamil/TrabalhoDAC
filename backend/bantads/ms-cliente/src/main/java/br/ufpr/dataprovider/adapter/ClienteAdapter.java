package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.output.SaveClientePortOut;
import br.ufpr.dataprovider.client.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements SaveClientePortOut {

  private final ClienteRepository repository;

  @Override
  public void save(Cliente cliente){
    ClienteEntity entity = new ClienteEntity();

    entity.setCpf(cliente.getCpf());
    entity.setNome(cliente.getNome());
    entity.setEmail(cliente.getEmail());
    entity.setTelefone(cliente.getTelefone());
    entity.setSenha(cliente.getSenha());
    entity.setSalario(cliente.getSalario());
    entity.setEndereco(cliente.getEndereco());

    repository.save(entity);
  }

  @Override
  public boolean existsByCpf(String cpf){
    return repository.existsByCpf(cpf);
  }
}
