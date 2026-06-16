package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.ClienteMovimentacoesDashboardOutputData;
import br.ufpr.core.domain.MovimentacaoOutputData;
import br.ufpr.dataprovider.client.domain.MovimentacaoResponse;
import br.ufpr.entrypoint.response.ClienteMovimentacoesDashboardResponse;
import br.ufpr.entrypoint.response.MovimentacaoEntrypointResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMovimentacoesDashboardAssembler {

  public ClienteMovimentacoesDashboardResponse assemble(ClienteMovimentacoesDashboardOutputData outputData){

    ClienteMovimentacoesDashboardResponse response = new ClienteMovimentacoesDashboardResponse();

    response.setClienteId(outputData.getClienteId());

    List<MovimentacaoEntrypointResponse> movimentacaoEntrypointResponseList = outputData.getMovimentacoes()
      .stream()
      .map(this::convertToResponseObject)
      .toList();


    response.setMovimentacoes(movimentacaoEntrypointResponseList);

    return response;
  }

  private MovimentacaoEntrypointResponse convertToResponseObject(MovimentacaoOutputData outputData){

    MovimentacaoEntrypointResponse response = new MovimentacaoEntrypointResponse();

    response.setValor(outputData.getValor());
    response.setDataHora(outputData.getDataHora());
    response.setMovimentacaoId(outputData.getMovimentacaoId());
    response.setClienteOrigemId(outputData.getClienteOrigemId());
    response.setClienteDestinoId(outputData.getClienteDestinoId());
    response.setTipoMovimentacao(outputData.getTipoMovimentacao());
    response.setClienteOrigemNome(outputData.getClienteOrigemNome());
    response.setClienteDestinoNome(outputData.getClienteDestinoNome());

    return response;
  }
}
