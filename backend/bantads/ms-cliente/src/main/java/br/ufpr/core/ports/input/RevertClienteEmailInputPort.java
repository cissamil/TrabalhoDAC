package br.ufpr.core.ports.input;

public interface RevertClienteEmailInputPort {

  void execute(String clienteId, String previousEmail);
}
