package br.ufpr.rabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String SAGA_EXCHANGE = "saga.autocadastro";
  public static  final String SAGA_QUEUE = "criar.credencial.queue";

  @Bean
  public MessageConverter jsonMessageConverter(){
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public DirectExchange exchange(){
    return new DirectExchange(SAGA_EXCHANGE);
  }

  @Bean
  public Queue queue(){
    return new Queue(SAGA_QUEUE, true);
  }

  @Bean
  public Binding binding(Queue queue, DirectExchange exchange){

    return BindingBuilder.bind(queue).to(exchange).with("criar.credencial");
  }



}
