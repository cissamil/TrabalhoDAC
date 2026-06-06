package br.ufpr.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailInputData {
  private String receiver;
  private String subject;
  private String message;
}
