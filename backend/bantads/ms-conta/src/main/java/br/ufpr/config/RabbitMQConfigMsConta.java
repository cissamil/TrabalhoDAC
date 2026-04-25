package br.ufpr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// @TODO CONSERTAR LISTENERS E PRODUCERS DE MENSAGERIA
@Configuration
public class RabbitMQConfigMsConta {

  public static final String REGISTER_EXCHANGE = "fluxo.autocadastro";
  public static final String REGISTER_QUEUE = "cadastrar-conta";

  @Bean
  public DirectExchange registerExchange(){
    return new DirectExchange(REGISTER_EXCHANGE);
  }

  @Bean
  public Queue registerQueue(){
    return new Queue(REGISTER_QUEUE, true, false, false);
  }

  @Bean
  public Binding bindingRegisterChannel(Queue registerQueue, DirectExchange registerExchange) {
    return BindingBuilder.bind(registerQueue).to(registerExchange).with("fluxo.autocadastro.key");
  }





}
