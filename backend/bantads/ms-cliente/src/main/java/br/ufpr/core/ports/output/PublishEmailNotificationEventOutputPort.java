package br.ufpr.core.ports.output;

public interface PublishEmailNotificationEventOutputPort {

  void publish(String email, String clienteId, String content, String subject);

}
