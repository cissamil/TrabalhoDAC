package br.ufpr.dataprovider.mapper;

import br.ufpr.core.domain.MovimentacaoOutputData;
import br.ufpr.dataprovider.client.domain.MovimentacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoResponseMapper {

  public MovimentacaoOutputData toResponse(MovimentacaoResponse response){

    if (response == null) return null;

    MovimentacaoOutputData outputData = new MovimentacaoOutputData();

    outputData.setClienteDestinoNome("");
    outputData.setValor(response.getValor());
    outputData.setDataHora(response.getDataHora());
    outputData.setMovimentacaoId(response.getMovimentacaoId());
    outputData.setClienteOrigemId(response.getClienteOrigemId());
    outputData.setClienteDestinoId(response.getClienteDestinoId());
    outputData.setTipoMovimentacao(response.getTipoMovimentacao());

    return outputData;
  }
}
