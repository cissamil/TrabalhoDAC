package br.ufpr.core.usecases;

import br.ufpr.core.domain.ClienteContaDashboardOutputData;
import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.ContaOutputData;
import br.ufpr.core.domain.GerenteOutputData;
import br.ufpr.core.ports.input.GroupClienteContaGerenteInputPort;
import br.ufpr.core.ports.output.ConsultClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.ConsultContaByClienteIdOutputPort;
import br.ufpr.core.ports.output.ConsultGerenteOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupClienteContaGerenteUseCase implements GroupClienteContaGerenteInputPort {

  private final ConsultGerenteOutputPort consultGerenteOutputPort;
  private final ConsultContaByClienteIdOutputPort consultContaByClienteIdOutputPort;
  private final ConsultClienteByClienteIdOutputPort consultClienteByClienteIdOutputPort;
  @Override
  public ClienteContaDashboardOutputData execute(String clienteId) {

    ClienteOutputData clienteOutputData = consultClienteByClienteIdOutputPort.consult(clienteId);
    ContaOutputData contaOutputData = consultContaByClienteIdOutputPort.consult(clienteId);
    validateOutputDatas(clienteOutputData, contaOutputData);

    GerenteOutputData gerenteOutputData = consultGerenteOutputPort.consult(contaOutputData.getGerenteId());

    validateGerente(gerenteOutputData);

    return new ClienteContaDashboardOutputData(clienteOutputData, contaOutputData, gerenteOutputData);
  }

  private static void validateGerente(GerenteOutputData gerenteOutputData) {
    if(gerenteOutputData == null){
      throw new RuntimeException("Gerente não encontrado");
    }
  }

  private static void validateOutputDatas(ClienteOutputData clienteOutputData, ContaOutputData contaOutputData) {
    if (clienteOutputData == null || contaOutputData == null){

      throw new RuntimeException("Cliente ou Conta não encontrados");

    }
  }
}
