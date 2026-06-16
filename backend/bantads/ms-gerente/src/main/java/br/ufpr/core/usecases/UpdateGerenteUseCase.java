package br.ufpr.core.usecases;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.ports.input.UpdateGerenteInputPort;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.core.ports.output.PublishUpdateUserEmailEventOutputPort;
import br.ufpr.core.ports.output.SaveGerenteOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ForbiddenResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateGerenteUseCase implements UpdateGerenteInputPort {

  private final SaveGerenteOutputPort saveGerenteOutputPort;
  private final FindGerenteByGerenteIdOutputPort findGerenteByGerenteIdOutputPort;
  private final PublishUpdateUserEmailEventOutputPort publishUpdateUserEmailEventOutputPort;

  @Override
  public void execute(String gerenteId, GerenteInputData inputData) {

    Gerente outdatedGerente =  findGerenteByGerenteIdOutputPort.find(gerenteId);

    validateGerente(outdatedGerente);

    Gerente updatedGerente = new Gerente();

    updatedGerente.setId(outdatedGerente.getId());
    updatedGerente.setGerenteId(gerenteId);
    updatedGerente.setEmail(inputData.getEmail());
    updatedGerente.setTelefone(inputData.getTelefone());
    updatedGerente.setNome(inputData.getNome());
    updatedGerente.setCpf(inputData.getCpf());
    updatedGerente.setTipoGerente(outdatedGerente.getTipoGerente());

    boolean emailHasChanged = !outdatedGerente.getEmail().equals(updatedGerente.getEmail());

    verifyIfCpfHasChanged(updatedGerente, outdatedGerente);

    saveGerenteOutputPort.save(updatedGerente);

    if (emailHasChanged) {

      String previousEmail = outdatedGerente.getEmail();

      publishUpdateUserEmailEventOutputPort.publish(gerenteId, updatedGerente.getEmail(), previousEmail);
    }
  }

  private void verifyIfCpfHasChanged(Gerente updatedGerenteData, Gerente outdatedGerenteData) {
    boolean cpfHasChanged  = !outdatedGerenteData.getCpf().equals(updatedGerenteData.getCpf());
    if(cpfHasChanged){
      throw new ForbiddenResourceException("CPF do Cliente não pode ser alterado");
    }
  }

  private void validateGerente(Gerente cliente) {
    if(cliente == null){
      throw new BusinessRuleException("Gerente não encontrado");
    }
  }
}
