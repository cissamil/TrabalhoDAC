package br.ufpr.config;

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
  public static final String SAGA_QUEUE_AUTOCADASTRO = "saga.autocadastro.queue";
  public static  final String SAGA_QUEUE_PEDIDO_APROVADO = "saga.pedido.aprovado.queue";
  public static  final String SAGA_QUEUE_PEDIDO_CRIADO = "saga.pedido.criado.queue";

  @Bean
  public MessageConverter jsonMessageConverter(){
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public DirectExchange exchange(){
    return new DirectExchange(SAGA_EXCHANGE);
  }

  @Bean
  public Queue queueAutocadastro(){return new Queue(SAGA_QUEUE_AUTOCADASTRO, true);}

  @Bean
  public Queue queuePedidoCriado(){
    return new Queue(SAGA_QUEUE_PEDIDO_CRIADO, true);
  }

  @Bean
  public Queue queuePedidoAprovado(){
    return new Queue(SAGA_QUEUE_PEDIDO_APROVADO, true);
  }


  // Binding para o autocadastro (ms-cliente -> ms-conta)
  @Bean
  public Binding bindingAutocadastro(Queue queueAutocadastro, DirectExchange exchange){
    return BindingBuilder.bind(queueAutocadastro).to(exchange).with("autocadastro.key");
  }

  // Binding para o pedido de criação de conta(ms-conta -> ms-gerente)
  @Bean
  public Binding bindingPedidoCriado(Queue queuePedidoCriado, DirectExchange exchange){
    return BindingBuilder.bind(queuePedidoCriado).to(exchange).with("pedido.criado.key");
  }

  // Binding para a aprovação do pedido de conta (ms-gerente -> ms-conta)
  @Bean
  public Binding bindingPedidoAprovado(Queue queuePedidoAprovado, DirectExchange exchange){
    return BindingBuilder.bind(queuePedidoAprovado).to(exchange).with("pedido.aprovado.key");
  }


}
