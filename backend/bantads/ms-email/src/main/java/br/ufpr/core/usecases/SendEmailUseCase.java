package br.ufpr.core.usecases;

import br.ufpr.core.domain.SendEmailInputData;
import br.ufpr.core.ports.SendEmailInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailUseCase implements SendEmailInputPort {


  private final JavaMailSender mailSender;

  @Override
  public void send(SendEmailInputData inputData) {

    System.out.println("Enviando e-mail para: " + inputData.getReceiver());

    SimpleMailMessage email = new SimpleMailMessage();

    email.setFrom("no-reply-bantads@gmail.com");
    email.setTo(inputData.getReceiver());
    email.setSubject(inputData.getSubject());
    email.setText(inputData.getMessage());

    mailSender.send(email);

    System.out.println("E-mail enviado com sucesso!");
  }
}
