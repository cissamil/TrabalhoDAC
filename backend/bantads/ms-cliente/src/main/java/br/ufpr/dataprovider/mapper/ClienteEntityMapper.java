package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.Endereco;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;
import br.ufpr.dataprovider.adapter.domain.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteEntityMapper {

  public Cliente toDomain(ClienteEntity entity){

    if(entity == null){
      return null;
    }

    Cliente cliente = new Cliente();


    Endereco endereco = getConvertedEnderecoToDomain(entity);

    cliente.setId(entity.getId());
    cliente.setClienteId(entity.getClienteId());
    cliente.setCpf(entity.getCpf());
    cliente.setNome(entity.getNome());
    cliente.setEmail(entity.getEmail());
    cliente.setTelefone(entity.getTelefone());
    cliente.setSalario(entity.getSalario());
    cliente.setEndereco(endereco);

    return cliente;

  }
  public ClienteEntity toEntity(Cliente cliente){

    if(cliente == null){
      return null;
    }

    ClienteEntity entity = new ClienteEntity();
    EnderecoEntity enderecoEntity = getConvertedEnderecoToEntity(cliente);

    entity.setId(cliente.getId());
    entity.setClienteId(cliente.getClienteId());
    entity.setCpf(cliente.getCpf());
    entity.setNome(cliente.getNome());
    entity.setEmail(cliente.getEmail());
    entity.setTelefone(cliente.getTelefone());
    entity.setSalario(cliente.getSalario());
    entity.setEndereco(enderecoEntity);

    return entity;
  }

  private Endereco getConvertedEnderecoToDomain(ClienteEntity entity) {

    EnderecoEntity enderecoEntity = entity.getEndereco();
    Endereco enderecoDomain = new Endereco();

    enderecoDomain.setCep(enderecoEntity.getCep());
    enderecoDomain.setNumero(enderecoEntity.getNumero());
    enderecoDomain.setCidade(enderecoEntity.getCidade());
    enderecoDomain.setEstado(enderecoEntity.getEstado());
    enderecoDomain.setLogradouro(enderecoEntity.getLogradouro());
    enderecoDomain.setId(enderecoEntity.getId());

    return enderecoDomain;
  }

  private EnderecoEntity getConvertedEnderecoToEntity(Cliente domain) {

    Endereco enderecoDomain = domain.getEndereco();
    EnderecoEntity enderecoEntity = new EnderecoEntity();

    enderecoEntity.setCep(enderecoDomain.getCep());
    enderecoEntity.setNumero(enderecoDomain.getNumero());
    enderecoEntity.setCidade(enderecoDomain.getCidade());
    enderecoEntity.setEstado(enderecoDomain.getEstado());
    enderecoEntity.setLogradouro(enderecoDomain.getLogradouro());
    enderecoEntity.setId(enderecoDomain.getId());

    return enderecoEntity;
  }


}
