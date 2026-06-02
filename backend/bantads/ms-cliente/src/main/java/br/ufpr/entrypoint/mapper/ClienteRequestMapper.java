package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.Endereco;
import br.ufpr.entrypoint.request.ClienteRequest;
import org.springframework.stereotype.Component;

/// CLASSE FEITA PARA CONVERSÃO GENÉRICA DIRETA DE ATRIBUTOS DE ENTIDADES
@Component
public class ClienteRequestMapper {

  public Cliente toDomain(ClienteRequest request){

    Endereco endereco = new Endereco(
      null,
      request.getEndereco().getCep(),
      request.getEndereco().getLogradouro(),
      request.getEndereco().getNumero(),
      request.getEndereco().getCidade(),
      request.getEndereco().getEstado()
    );


    return new Cliente(
      null, // ID gerado pelo banco
      null,
      request.getCpf(),
      request.getNome(),
      request.getEmail(),
      request.getTelefone(),
      request.getSalario(),
      endereco
    );
  }

}
