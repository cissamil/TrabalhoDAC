package br.ufpr.core.ports.output;

public interface PublishUpdateUserEmailEventOutputPort {

  void publish(String userId, String userEmail );
}
