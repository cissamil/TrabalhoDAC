package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.ports.output.ConsultClientesListFromIdsOutputPort;
import br.ufpr.core.ports.output.ConsultGerentesListFromIdsOutputPort;
import br.ufpr.dataprovider.client.MsClienteClient;
import br.ufpr.dataprovider.client.MsGerenteClient;
import br.ufpr.dataprovider.client.domain.ClienteResponse;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import br.ufpr.dataprovider.mapper.ClienteResponseMapper;
import br.ufpr.dataprovider.mapper.GerenteResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultGerentesListFromIdsAdapter implements ConsultGerentesListFromIdsOutputPort{

  private final MsGerenteClient msGerenteClient;
  private final GerenteResponseMapper mapper;

  @Override
  public List<GerenteOutputData> consult(List<String> gerenteIds) {

    List<GerenteResponse> responseList = msGerenteClient.consultGerentesListFromIds(gerenteIds);

    List<GerenteOutputData> outputDataList = responseList.stream().map(mapper::toOutputData).toList();

    return outputDataList;
  }
}
