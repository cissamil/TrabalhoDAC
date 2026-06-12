package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.Movimentacao;
import br.ufpr.entrypoint.response.MovimentacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoResponseMapper {

  public MovimentacaoResponse toResponse(Movimentacao domain){

    if (domain == null) return null;

    MovimentacaoResponse response = new MovimentacaoResponse();

    response.setDataHora(domain.getDataHora());
    response.setValor(domain.getValor());
    response.setMovimentacaoId(domain.getMovimentacaoId());
    response.setClienteOrigemId(domain.getClienteOrigemId());
    response.setClienteDestinoId(domain.getClienteDestinoId());
    response.setTipoMovimentacao(domain.getTipoMovimentacao());


    return response;
  }
}
