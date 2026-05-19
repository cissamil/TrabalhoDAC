package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.entrypoint.request.GerenteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class GerenteRequestMapper {

  public GerenteInputData toInputData(GerenteRequest request){

    if (request == null) return null;

    GerenteInputData inputData = new GerenteInputData();

    inputData.setCpf(request.getCpf());
    inputData.setNome(request.getNome());
    inputData.setEmail(request.getEmail());

    return inputData;
  }
}
