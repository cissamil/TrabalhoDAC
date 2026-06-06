package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.mapper.ClienteResponseMapper;
import feign.FeignException;
import br.ufpr.infrastructure.exceptions.UnavailableServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultClientesListFromIdsAdapter implements ConsultClientesListFromIdsOutputPort {

  private final MsClienteClient msClienteClient;
  private final ClienteResponseMapper mapper;

  @Override
  public List<ClienteOutputData> consult(List<String> clienteIds) {
    try{

      List<ClienteResponse> responseList = msClienteClient.consultClientesListFromIds(clienteIds);

      List<ClienteOutputData> outputDataList = responseList.stream().map(mapper::toOutputData).toList();

      return outputDataList;

    }catch (FeignException e){

      throw new UnavailableServiceException("Serviço de clientes indisponível");
    }
  }
}
