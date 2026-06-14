package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.FindGerenteTotalClientesOutputPort;
import br.ufpr.core.domain.GerenteTotalClientesOutputData;
import br.ufpr.dataprovider.adapter.domain.GerenteTotalClientesResponse;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindGerenteTotalClientesAdapter implements FindGerenteTotalClientesOutputPort {

  private final ContaQueryRepository repository;

  @Override
  public List<GerenteTotalClientesOutputData> find() {

    List<GerenteTotalClientesResponse> gerentesTotalClientes = repository.findGerentesTotalClientes();

    return gerentesTotalClientes.stream().map(response -> {

      GerenteTotalClientesOutputData outputData = new GerenteTotalClientesOutputData();

      outputData.setGerente(response.getGerente());
      outputData.setQtdClientes(response.getQtdClientes().intValue());
      outputData.setMenorSaldoPositivo(response.getMenorSaldoPositivo());

      return outputData;
    }).toList();
  }
}
