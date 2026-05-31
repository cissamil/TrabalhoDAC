package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.RemoveGerenteByGerenteIdOutputPort;
import br.ufpr.dataprovider.client.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveGerenteByGerenteIdAdapter implements RemoveGerenteByGerenteIdOutputPort {

  // @TODO IMPEDIR QUE O GERENTE SEJA REMOVIDO CASO ELE SEJA O ÚLTIMO GERENTE DO BANCO.
  private final GerenteRepository repository;

  @Override
  public void remove(String gerenteId) {

    try{

      System.out.println("Deletando conta do gerente");

      repository.deleteByGerenteId(gerenteId);

      System.out.println("Conta deletada com sucesso");


    }catch (Exception e){
      throw new RuntimeException(e);
    }

  }
}
