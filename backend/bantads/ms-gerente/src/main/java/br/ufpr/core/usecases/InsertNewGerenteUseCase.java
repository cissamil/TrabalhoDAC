package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.domain.TipoGerente;
import br.ufpr.core.ports.input.InsertNewGerenteInputPort;
import br.ufpr.core.ports.output.FindGerenteByCpfOutputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.core.ports.output.PublishCreatedGerenteAccountEventOutputPort;
import br.ufpr.core.ports.output.SaveGerenteOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InsertNewGerenteUseCase implements InsertNewGerenteInputPort {

  private final SaveGerenteOutputPort saveGerenteOutputPort;
  private final FindGerenteByCpfOutputPort findGerenteByCpfOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;
  private final PublishCreatedGerenteAccountEventOutputPort publishCreatedGerenteAccountEventOutputPort;

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


    saveGerenteOutputPort.save(gerente);
    publishCreatedGerenteAccountEventOutputPort.publish(gerente);

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
}
