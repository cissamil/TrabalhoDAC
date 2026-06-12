package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteTotalClientesOutputPort;
import br.ufpr.core.usecases.GerenteTotalClientesOutputData;
import br.ufpr.dataprovider.adapter.domain.GerenteTotalClientesResponse;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerenteTotalClientesAdapter implements FindGerenteTotalClientesOutputPort {

  private final ContaCommandRepository contaCommandRepository;

  @Override
  public List<GerenteTotalClientesOutputData> find() {

    List<GerenteTotalClientesResponse> gerentesTotalClientes = contaCommandRepository.findGerentesTotalClientes();

    return gerentesTotalClientes.stream().map(response -> {

      GerenteTotalClientesOutputData outputData = new GerenteTotalClientesOutputData();

      outputData.setGerente(response.getGerente());
      outputData.setQtdClientes(response.getQtdClientes().intValue());
      outputData.setMenorSaldoPositivo(response.getMenorSaldoPositivo());

      return outputData;
    }).toList();
  }
}
