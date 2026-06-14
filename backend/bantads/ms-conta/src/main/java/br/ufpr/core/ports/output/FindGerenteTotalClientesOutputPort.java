package br.ufpr.core.ports.output;

import br.ufpr.core.domain.GerenteTotalClientesOutputData;

import java.util.List;

public interface FindGerenteTotalClientesOutputPort {

  List<GerenteTotalClientesOutputData> find();
}
