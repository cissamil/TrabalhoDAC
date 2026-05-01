package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Conta;
import br.ufpr.model.response.PendingContaResponse;
import org.springframework.stereotype.Component;

@Component
public class PendingContaResponseMapper {

  public PendingContaResponse toResponse(Conta conta){

    if(conta == null) return null;

    PendingContaResponse response = new PendingContaResponse();


    response.setId(conta.getId());
    response.setClienteId(conta.getClienteId());
    response.setGerenteId(conta.getGerenteId());

    return response;
  }
}
