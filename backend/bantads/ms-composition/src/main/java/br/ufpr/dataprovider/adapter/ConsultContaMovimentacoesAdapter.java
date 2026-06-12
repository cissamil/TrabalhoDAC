package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ConsultBankStatementInputData;
import br.ufpr.core.domain.MovimentacaoOutputData;
import br.ufpr.core.ports.output.ConsultContaMovimentacoesOutputPort;
import br.ufpr.dataprovider.client.MsContaClient;
import br.ufpr.dataprovider.client.domain.MovimentacaoResponse;
import br.ufpr.dataprovider.mapper.MovimentacaoResponseMapper;
import feign.FeignException;
import br.ufpr.infrastructure.exceptions.UnavailableServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultContaMovimentacoesAdapter implements ConsultContaMovimentacoesOutputPort {

  private final MsContaClient msContaClient;
  private final MovimentacaoResponseMapper mapper;

  @Override
  public List<MovimentacaoOutputData> consult(ConsultBankStatementInputData inputData) {

    try{

      String clienteId = inputData.getClienteId();
      LocalDate dataInicio = inputData.getDataInicio();
      LocalDate dataFim = inputData.getDataFim();

      List<MovimentacaoResponse> responseList = msContaClient.consultContaMovimentacoes(clienteId, dataInicio, dataFim);

      return responseList.stream().map(mapper::toResponse).toList();

    }catch (FeignException e){
      throw new UnavailableServiceException("Serviço de contas indisponível");

    }


  }
}
