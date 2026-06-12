package br.ufpr.core.ports.output;

public interface PublishContaRefusedEventOutputPort {

  void publish(String clienteId, String motivo);
}
