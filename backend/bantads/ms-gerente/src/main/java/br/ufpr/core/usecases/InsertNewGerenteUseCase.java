package br.ufpr.core.usecases;

import br.ufpr.core.domain.GerenteEventPublisher;
import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.domain.TipoGerente;
import br.ufpr.core.ports.input.InsertNewGerenteInputPort;
import br.ufpr.core.ports.output.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InsertNewGerenteUseCase implements InsertNewGerenteInputPort {

  private final SaveGerenteOutputPort saveGerenteOutputPort;
  private final FindGerenteByCpfOutputPort findGerenteByCpfOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;
  private final PublishAssignContaToGerenteEventOutputPort publishAssignContaToGerenteEventOutputPort;
  private final PublishCreateGerenteCredentialEventOutputPort publishCreateGerenteCredentialEventOutputPort;

  @Override
  public void execute(GerenteInputData inputData) {

    Gerente gerente = new Gerente();

    gerente.setId(null);
    gerente.setCpf(inputData.getCpf());
    gerente.setNome(inputData.getNome());
    gerente.setEmail(inputData.getEmail());
    gerente.setTipoGerente(TipoGerente.GERENTE);

    validateGerente(gerente);

    String gerenteId = generateValidGerenteId();

    gerente.setGerenteId(gerenteId);

    String senha = inputData.getSenha();


    saveGerenteOutputPort.save(gerente);
    publishAssignContaToGerenteEventOutputPort.publish(gerente);
    publishEventMessage(gerente, senha);

  }

  private void validateGerente(Gerente gerente) {
    if(findGerenteByCpfOutputPort.exists(gerente.getCpf())){
      throw new RuntimeException("CPF já cadastrado");
    }
  }

  private String generateValidGerenteId() {

    String gerenteId = UUID.randomUUID().toString();

    while(findGerenteByGerenteIdOutputPort.exists(gerenteId)){

      gerenteId = UUID.randomUUID().toString();
    }

    return gerenteId;
  }

  private void publishEventMessage(Gerente gerente, String senha){

    GerenteEventPublisher eventPublisher = new GerenteEventPublisher();

    eventPublisher.setGerenteId(gerente.getGerenteId());
    eventPublisher.setEmail(gerente.getEmail());
    eventPublisher.setSenha(senha);

    publishCreateGerenteCredentialEventOutputPort.publish(eventPublisher);
  }
}
