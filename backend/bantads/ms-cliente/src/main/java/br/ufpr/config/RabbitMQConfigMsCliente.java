package br.ufpr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitMQConfigMsCliente {

  public static final String REGISTER_EXCHANGE = "fluxo.autocadastro";
  public static final String REGISTER_QUEUE = "cadastrar-conta";

  public static final String UPDATE_USER_EMAIL_EXCHANGE = "fluxo.login-usuario";
  public static final String UPDATE_USER_EMAIL_QUEUE = "atualizar-login-usuario";

  public static final String UPDATE_ACCOUNT_LIMIT_EXCHANGE = "fluxo.atualizar-limite";
  public static final String UPDATE_ACCOUNT_LIMIT_QUEUE = "atualizar-limite";


  @Bean
  public DirectExchange registerExchange(){
    return new DirectExchange(REGISTER_EXCHANGE);
  }

  @Bean
  public Queue registerQueue(){
    return new Queue(REGISTER_QUEUE, true, false, false);
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
  public DirectExchange updateAccountLimitExchange(){
    return new DirectExchange(UPDATE_ACCOUNT_LIMIT_EXCHANGE);
  }

  @Bean
  public Queue updateAccountLimitQueue(){
    return new Queue(UPDATE_ACCOUNT_LIMIT_QUEUE, true, false, false);
  }


  @Bean
  public Binding bindingRegisterChannel(Queue registerQueue, DirectExchange registerExchange){
    return BindingBuilder.bind(registerQueue).to(registerExchange).with("fluxo.autocadastro.key");
  }

  @Bean
  public Binding bindingUpdateUserEmailChannel(Queue updateUserEmailQueue, DirectExchange updateUserEmailExchange) {
    return BindingBuilder.bind(updateUserEmailQueue).to(updateUserEmailExchange).with("fluxo.atualizar-login-usuario.key");
  }

  @Bean
  public Binding bindingUpdateAccountLimitChannel(Queue updateAccountLimitQueue, DirectExchange updateAccountLimitExchange) {
    return BindingBuilder.bind(updateAccountLimitQueue).to(updateAccountLimitExchange).with("fluxo.atualizar-limite.key");
  }


}
