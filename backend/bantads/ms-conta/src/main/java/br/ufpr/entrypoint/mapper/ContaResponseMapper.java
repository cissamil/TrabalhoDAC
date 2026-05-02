package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Conta;
import br.ufpr.entrypoint.response.ContaResponse;
import org.springframework.stereotype.Component;

@Component
public class ContaResponseMapper {

  public Conta toDomain(ContaResponse response){

    if(response == null) return null;

    Conta conta = new Conta();

    conta.setId(response.getId());
    conta.setSaldo(response.getSaldo());
    conta.setLimite(response.getLimite());
    conta.setClienteId(response.getClienteId());
    conta.setGerenteId(response.getGerenteId());
    conta.setNumeroConta(response.getNumeroConta());
    conta.setDataCriacao(response.getDataCriacao());
    conta.setStatusConta(response.getStatusConta());
    conta.setDataDecisao(response.getDataDecisao());
    conta.setMotivoRecusa(response.getMotivoRecusa());

    return conta;
  }

  public ContaResponse toResponse(Conta conta){

    if(conta == null) return null;

    ContaResponse response = new ContaResponse();

    response.setId(conta.getId());
    response.setSaldo(conta.getSaldo());
    response.setLimite(conta.getLimite());
    response.setClienteId(conta.getClienteId());
    response.setGerenteId(conta.getGerenteId());
    response.setNumeroConta(conta.getNumeroConta());
    response.setDataCriacao(conta.getDataCriacao());
    response.setStatusConta(conta.getStatusConta());
    response.setDataDecisao(conta.getDataDecisao());
    response.setMotivoRecusa(conta.getMotivoRecusa());

    return response;
  }
}
