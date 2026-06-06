package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.ports.output.ConsultGerentesOutputPort;
import br.ufpr.dataprovider.client.MsGerenteClient;
import br.ufpr.dataprovider.client.domain.GerenteResponse;
import br.ufpr.dataprovider.mapper.GerenteResponseMapper;
import feign.FeignException;
import br.ufpr.infrastructure.exceptions.UnavailableServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultGerentesAdapter implements ConsultGerentesOutputPort {

  private final MsGerenteClient msGerenteClient;
  private final GerenteResponseMapper mapper;

  @Override
  public List<GerenteOutputData> consult() {

    try{

      List<GerenteResponse> responseList = msGerenteClient.consultGerentes();

      return responseList
        .stream()
        .map(mapper::toOutputData)
        .toList();

    }catch (FeignException e){

      throw new UnavailableServiceException("Serviço de gerentes indisponível");

    }

  }
}
