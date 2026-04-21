package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.SaveClientePortIn;
import br.ufpr.core.ports.output.SaveClientePortOut;
import br.ufpr.core.ports.output.SendSagaPortOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequiredArgsConstructor
public class SaveClienteUseCase implements SaveClientePortIn {

  private final SaveClientePortOut saveClientePortOut;
  private final SendSagaPortOut sendSagaPortOut;

  @Override
  public void execute (Cliente cliente){

    if (saveClientePortOut.existsByCpf(cliente.getCpf())){
      throw new RuntimeException("CPF já cadastrado");
    }


    saveClientePortOut.save(cliente);

    sendSagaPortOut.send(cliente);
  }

}
