package br.ufpr.core.ports.output;

public interface PublishUserCredentialDeletedEventOutputPort {

  void publish(String userId);
}
