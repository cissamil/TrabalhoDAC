package br.ufpr.dataprovider.adapter.domain;

import java.math.BigDecimal;

public interface GerenteTotalClientesResponse {

  String getGerente();
  Long getQtdClientes();
  BigDecimal getMenorSaldoPositivo();
}
