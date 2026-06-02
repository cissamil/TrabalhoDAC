package br.ufpr.entrypoint.mapper;

import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.entrypoint.request.AddGerenteRequest;
import org.springframework.stereotype.Component;

@Component
public class GerenteRequestMapper {

  public GerenteInputData toInputData(AddGerenteRequest request){

    if (request == null) return null;

    GerenteInputData inputData = new GerenteInputData();

    inputData.setCpf(request.getCpf());
    inputData.setNome(request.getNome());
    inputData.setEmail(request.getEmail());
    inputData.setSenha(request.getSenha());
    inputData.setTelefone(request.getTelefone());

    return inputData;
  }
}
