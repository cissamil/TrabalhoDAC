package br.ufpr.core.ports.output;

public interface PublishUpdateUserEmailEventOutputPort {

  void publish(String gerenteId, String newGerenteEmail, String previousGerenteEmail );
}
