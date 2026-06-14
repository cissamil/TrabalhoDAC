package br.ufpr.core.ports.output;

public interface PublishUpdateUserEmailEventOutputPort {

  void publish(String clienteId, String newClienteEmail, String previousClienteEmail );
}
