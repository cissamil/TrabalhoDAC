package br.ufpr.core.ports.output;

public interface PublishClienteNotificationReadyOutputPort {

  void publish(String email, String clienteId, String content);
}
