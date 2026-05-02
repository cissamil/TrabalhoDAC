package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Gerente;
import br.ufpr.entrypoint.response.GerenteResponse;
import org.springframework.stereotype.Component;

@Component
public class GerenteResponseMapper {

  public GerenteResponse toResponse(Gerente gerente){

    if (gerente == null) return null;

    GerenteResponse response = new GerenteResponse();

    response.setId(gerente.getId());
    response.setCpf(gerente.getCpf());
    response.setNome(gerente.getNome());
    response.setEmail(gerente.getEmail());
    response.setGerenteId(gerente.getGerenteId());
    response.setTipoGerente(gerente.getTipoGerente());

    return response;
  }
}
