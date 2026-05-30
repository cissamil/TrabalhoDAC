package br.ufpr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigMsAuth {


  public static final String APPROVED_ACCOUNT_EXCHANGE = "fluxo.conta-aprovada";
  public static final String APPROVED_ACCOUNT_QUEUE = "gerar-credencial-cliente";

  public static final String GENERATE_MANAGER_CREDENTIAL_EXCHANGE = "fluxo.credencial-gerente";
  public static final String GENERATE_MANAGER_CREDENTIAL_QUEUE = "gerar-credencial-gerente";

  public static final String UPDATE_USER_EMAIL_EXCHANGE = "fluxo.login-usuario";
  public static final String UPDATE_USER_EMAIL_QUEUE = "atualizar-login-usuario";

  @Bean
  public DirectExchange approvedAccountExchange(){
    return new DirectExchange(APPROVED_ACCOUNT_EXCHANGE);
  }

  @Bean
  public Queue approvedAccountQueue(){
    return new Queue(APPROVED_ACCOUNT_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange updateUserEmailExchange(){
    return new DirectExchange(UPDATE_USER_EMAIL_EXCHANGE);
  }

  @Bean
  public Queue updateUserEmailQueue(){
    return new Queue(UPDATE_USER_EMAIL_QUEUE, true, false, false);
  }

  @Bean
  public DirectExchange generateManagerCredentialExchange(){
    return new DirectExchange(GENERATE_MANAGER_CREDENTIAL_EXCHANGE);
  }

  @Bean
  public Queue generateManagerCredentialQueue(){
    return new Queue(GENERATE_MANAGER_CREDENTIAL_QUEUE, true, false, false);
  }

  @Bean
  public Binding bindingApprovedAccountChannel(Queue approvedAccountQueue, DirectExchange approvedAccountExchange) {
    return BindingBuilder.bind(approvedAccountQueue).to(approvedAccountExchange).with("fluxo.conta-aprovada.key");
  }

  @Bean
  public Binding bindingGenerateManagerCredentialChannel(Queue generateManagerCredentialQueue, DirectExchange generateManagerCredentialExchange) {
    return BindingBuilder.bind(generateManagerCredentialQueue).to(generateManagerCredentialExchange).with("fluxo.gerar-credencial-gerente.key");
  }

  @Bean
  public Binding bindingUpdateUserEmailChannel(Queue updateUserEmailQueue, DirectExchange updateUserEmailExchange) {
    return BindingBuilder.bind(updateUserEmailQueue).to(updateUserEmailExchange).with("fluxo.atualizar-login-usuario.key");
  }

}




