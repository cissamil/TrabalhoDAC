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
  public static final String APPROVED_ACCOUNT_QUEUE = "gerar-credencial";

  @Bean
  public DirectExchange approvedAccountExchange(){
    return new DirectExchange(APPROVED_ACCOUNT_EXCHANGE);
  }

  @Bean
  public Queue approvedAccountQueue(){
    return new Queue(APPROVED_ACCOUNT_QUEUE, true, false, false);
  }

  @Bean
  public Binding bindingApprovedAccountChannel(Queue registerQueue, DirectExchange registerExchange) {
    return BindingBuilder.bind(registerQueue).to(registerExchange).with("fluxo.conta-aprovada.key");
  }
}
