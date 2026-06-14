package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.UpdateClienteInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.PublishUpdateContaLimitEventOutputPort;
import br.ufpr.core.ports.output.PublishUpdateUserEmailEventOutputPort;
import br.ufpr.core.ports.output.SaveClienteOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ForbiddenResourceException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateClienteUseCase implements UpdateClienteInputPort {

  private final SaveClienteOutputPort saveClienteOutputPort;
  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;
  private final PublishUpdateUserEmailEventOutputPort publishUpdateUserEmailEventOutputPort;
  private final PublishUpdateContaLimitEventOutputPort publishUpdateContaLimitEventOutputPort;

  @Override
  public void execute(Cliente updatedClienteData) {

    validateClienteId(updatedClienteData);

    Cliente outdatedClienteData = getValidOutdatedClienteData(updatedClienteData);

    updatedClienteData.setId(outdatedClienteData.getId());

    String clienteId = updatedClienteData.getClienteId();
    String clienteEmail = updatedClienteData.getEmail();
    BigDecimal clienteSalary = updatedClienteData.getSalario();

    boolean emailHasChanged = !outdatedClienteData.getEmail().equals(updatedClienteData.getEmail());
    boolean salaryHasChanged = !outdatedClienteData.getSalario().equals(updatedClienteData.getSalario());

    verifyIfCpfHasChanged(updatedClienteData, outdatedClienteData);

    saveClienteOutputPort.save(updatedClienteData);

    if (emailHasChanged) {

      String previousEmail = outdatedClienteData.getEmail();

      publishUpdateUserEmailEventOutputPort.publish(clienteId, clienteEmail, previousEmail);
    }

    if(salaryHasChanged){
      publishUpdateContaLimitEventOutputPort.publish(clienteId, clienteSalary);
    }
  }

  private static void verifyIfCpfHasChanged(Cliente updatedClienteData, Cliente outdatedClienteData) {
    boolean cpfHasChanged  = !outdatedClienteData.getCpf().equals(updatedClienteData.getCpf());
    if(cpfHasChanged){
      throw new ForbiddenResourceException("CPF do Cliente não pode ser alterado");
    }
  }

  private Cliente getValidOutdatedClienteData(Cliente cliente) {
    Cliente outdatedClienteData = findClienteByClienteIdOutputPort.find(cliente.getClienteId());

    if(outdatedClienteData == null){
      throw new ResourceNotFoundException("Cliente não encontrado no banco. Dados não atualizados");
    }

    return outdatedClienteData;
  }

  private void validateClienteId(Cliente cliente) {
    if(cliente.getClienteId() == null){
      throw new BusinessRuleException("Id do cliente não pode ser nulo");
    }
  }
}
