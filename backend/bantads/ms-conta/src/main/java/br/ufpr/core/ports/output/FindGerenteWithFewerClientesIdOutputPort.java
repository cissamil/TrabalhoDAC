package br.ufpr.core.ports.output;

public interface FindGerenteWithFewerClientesIdOutputPort {

  String find();
  String findWithoutSelectedGerente(String gerenteId);
}
