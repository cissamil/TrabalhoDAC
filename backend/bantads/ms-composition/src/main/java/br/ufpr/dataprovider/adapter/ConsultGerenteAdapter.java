package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.ports.output.ConsultGerenteOutputPort;
import br.ufpr.dataprovider.client.MsGerenteClient;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import br.ufpr.dataprovider.mapper.GerenteResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultGerenteAdapter implements ConsultGerenteOutputPort {

  private final MsGerenteClient msGerenteClient;
  private final GerenteResponseMapper mapper;

  @Override
  public GerenteOutputData consult(String gerenteId) {

    GerenteResponse response = msGerenteClient.consultGerente(gerenteId);

    return mapper.toOutputData(response);
  }
}
