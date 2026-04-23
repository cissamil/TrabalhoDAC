package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.dataprovider.adapter.domain.ClienteEntity;

public class ClienteEntityMapper {

  public Cliente toDomain(ClienteEntity entity){

    if(entity == null){
      return null;
    }

    Cliente cliente = new Cliente();

    cliente.setId(entity.getId());
    cliente.setClienteId(entity.getClienteId());
    cliente.setCpf(entity.getCpf());
    cliente.setNome(entity.getNome());
    cliente.setEmail(entity.getEmail());
    cliente.setTelefone(entity.getTelefone());
    cliente.setSalario(entity.getSalario());
    cliente.setEndereco(entity.getEndereco());

    return cliente;

  }

  public ClienteEntity toEntity(Cliente cliente){

    if(cliente == null){
      return null;
    }

    ClienteEntity entity = new ClienteEntity();

    entity.setId(cliente.getId());
    entity.setClienteId(cliente.getClienteId());
    entity.setCpf(cliente.getCpf());
    entity.setNome(cliente.getNome());
    entity.setEmail(cliente.getEmail());
    entity.setTelefone(cliente.getTelefone());
    entity.setSalario(cliente.getSalario());
    entity.setEndereco(cliente.getEndereco());

    return entity;
  }

}
