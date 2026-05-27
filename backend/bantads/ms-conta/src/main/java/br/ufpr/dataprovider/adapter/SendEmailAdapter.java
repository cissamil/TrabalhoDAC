package br.ufpr.dataprovider.adapter;

import br.ufpr.core.ports.output.SendEmailOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailAdapter implements SendEmailOutputPort{

  private final JavaMailSender mailSender;

  @Override
  public void send(String receiver, String subject, String message) {

    System.out.println("Enviando e-mail para: " + receiver);

    SimpleMailMessage email = new SimpleMailMessage();

    email.setFrom("no-reply-bantads@gmail.com");
    email.setTo(receiver);
    email.setSubject(subject);
    email.setText(message);

    mailSender.send(email);

    System.out.println("E-mail enviado com sucesso!");
  }
}
