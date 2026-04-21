package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Cliente;
import br.ufpr.entrypoint.request.ClienteRequest;
import org.springframework.stereotype.Component;

/// CLASSE FEITA PARA CONVERSÃO GENÉRICA DIRETA DE ATRIBUTOS DE ENTIDADES
@Component
public class ClienteMapper {

  public Cliente toDomain(ClienteRequest request){
    String enderecoConcatenado = String.format("%s - %s - %s - %s",
      request.getCep(),
      request.getLogradouro(),
      request.getCidade(),
      request.getEstado()
    );

    return new Cliente(
      null, // ID gerado pelo banco
      request.getCpf(),
      request.getNome(),
      request.getEmail(),
      request.getTelefone(),
      request.getSenha(),
      request.getSalario(),
      enderecoConcatenado
    );
  }
}
