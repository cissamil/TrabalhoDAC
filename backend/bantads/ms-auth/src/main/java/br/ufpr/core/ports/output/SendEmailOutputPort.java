package br.ufpr.core.ports.output;

public interface SendEmailOutputPort {
  void send(String receiver, String subject, String message);
}
