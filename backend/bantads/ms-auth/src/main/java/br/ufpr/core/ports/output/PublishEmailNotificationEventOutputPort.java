package br.ufpr.core.ports.output;

public interface PublishEmailNotificationEventOutputPort {

  void publish(String email, String userId, String content, String subject);

}
