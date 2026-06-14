package br.ufpr.core.ports.output;

public interface PublishUserNotificationReadyOutputPort {

  void publish(String email, String userId, String content, String subject);
}
